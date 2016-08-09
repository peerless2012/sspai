package com.peerless2012.sspai.data.source.remote;

import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.peerless2012.sspai.common.utils.FileUtils;
import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.data.source.RemoteDataSource;
import com.peerless2012.sspai.data.threads.ExecutorCallBack;
import com.peerless2012.sspai.data.threads.ExecutorRunnable;
import com.peerless2012.sspai.data.threads.WorkExecutor;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.ArticleDetail;
import com.peerless2012.sspai.domain.Articles;
import com.peerless2012.sspai.domain.News;
import com.peerless2012.sspai.domain.NewsItem;
import com.peerless2012.sspai.domain.NewsType;
import com.peerless2012.sspai.net.HttpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 16:49
 * @Version V1.0
 * @Description :
 */
public class RemoteDataSourceImpl implements RemoteDataSource {

    private final static String BASE_URL = "http://sspai.com/tag/";
    private static final String TAG = "RemoteDataSourceImpl";

    private Context mContext;

    private static volatile RemoteDataSourceImpl sInst = null;  // <<< 这里添加了 volatile

    private RemoteDataSourceImpl(Context context) {
        mContext = context.getApplicationContext();
    }

    public static RemoteDataSourceImpl getInstance(Context context) {
        RemoteDataSourceImpl inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (RemoteDataSourceImpl.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new RemoteDataSourceImpl(context);
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }
    // http://sspai.com/tag/android/?t=%E6%89%8B%E6%9C%BA&page=2
    // http://sspai.com/tag/android/?t=手机&page=2
    @Override
    public void loadNews(@NonNull final NewsType newsType, final int pageIndex,@NonNull final SimpleCallBack<List<Article>> callBack) {
        WorkExecutor.getInstance().execute(new ExecutorRunnable<List<Article>>(new ExecutorCallBack<List<Article>>() {
            @Override
            public List<Article> doInBackground() {
                StringBuilder urlBuilder = new StringBuilder(BASE_URL);
                try {
                    urlBuilder
                            .append(URLEncoder.encode(newsType.getTopicTag(),"UTF-8"))
                            .append("/?t=")
                            .append(URLEncoder.encode(newsType.getTypeName(),"UTF-8"))
                            .append("&page=")
                            .append(pageIndex);
                    OkHttpClient okHttpClient = HttpUtils.getInstance().getOkHttpClient();
                    Request request = new Request.Builder()
                            .url(urlBuilder.toString())
                            .get()
                            .build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        Response response = call.execute();
                        if (response != null && response.code() == 200){
                            Articles articles = null;
                            try {
                                String html = response.body().string();
                                Document document = Jsoup.parse(html, "");
                                articles = new Articles();
                                // 获取总数
                                Element countElement = document.getElementsByClass("count").first();
                                String count = null;
                                if (countElement != null) {
                                    String countStr = countElement.html();
                                    Pattern pattern = Pattern.compile("[0-9]+");
                                    Matcher matcher = pattern.matcher(countStr);
                                    if (matcher.find()) {
                                        count = matcher.group(0);
                                        articles.setCount(Integer.parseInt(count));
                                    }
                                }

                                System.out.println(""+count);

                                //获取图片
                                List<Article> articleList = new ArrayList<Article>();
                                Elements banner = document.getElementsByClass("banner");
                                for (int i = 0; i < banner.size(); i++) {
                                    Element element = banner.get(i);
                                    System.out.println("           "+i+"                                 ");
                                    Elements elementsByTag = element.getElementsByTag("img");
                                    String url = elementsByTag.get(0).attr("data-src");

                                    Article article = new Article();
                                    article.setImgUrl(url);
                                    article.setTopicId(newsType.getTopicId());
                                    article.setNewsTypeId(newsType.getTypeId());
                                    articleList.add(article);
                                    System.out.println(url);
                                    System.out.println("                                            ");
                                }

                                System.out.println("---------------------------------------------------------");
                                System.out.println("---------------------------------------------------------");
                                System.out.println("---------------------------------------------------------");

                                // 获取标题信息
                                Elements ha = document.getElementsByClass("ha");
                                int index = 0;
                                for (int i = 0; i < ha.size(); i++) {
                                    Element element = ha.get(i);
                                    Elements desc = element.getElementsByClass("desc");
                                    if (desc != null && desc.size() > 0) {
                                        Element titleElement = element.getElementsByClass("title").first().getElementsByTag("a").first();
                                        String url = titleElement.attr("href");
                                        String titleStr = titleElement.html();

                                        String descStr;
                                        Element descElement = element.getElementsByClass("desc").first();
                                        Element first = descElement.getElementsByTag("a").first();
                                        if (first != null) {
                                            descStr = first.html();
                                        }else {
                                            descStr = descElement.html();

                                        }
                                        Article article = articleList.get(index);
                                        int split = url.lastIndexOf("/");
                                        String idStr = url.substring(split+1);
                                        article.setArticleUrl(url);
                                        article.setArticleId(idStr);
                                        article.setTitle(titleStr);
                                        article.setDesc(descStr);
                                        System.out.println("              "+index+"                              ");
                                        System.out.println(url);
                                        System.out.println(titleStr);
                                        System.out.println(descStr);
                                        System.out.println("                                            ");
                                        index++;
                                    }
                                }

                                // 时间
                                Elements publishTimeElements = document.getElementsByClass("publish-time");
                                if (publishTimeElements != null && publishTimeElements.size() >0) {
                                    int size = publishTimeElements.size();
                                    for (int i = 0; i < size; i++) {
                                        Article article = articleList.get(i);
                                        String publishTime = publishTimeElements.get(i).html();
                                        article.setPublishTime(publishTime);
                                        System.out.println("" + i);
                                        System.out.println(publishTime);
                                    }
                                }

                                return articleList;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public void onPostExecute(List<Article> articles) {
                if (callBack != null) callBack.onLoaded(articles);
            }
        }));
    }

    @Override
    public void loadNewsDetail(@NonNull final Article article, @NonNull final SimpleCallBack<ArticleDetail> callBack) {
        OkHttpClient okHttpClient = HttpUtils.getInstance().getOkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(article.getArticleUrl())
                .build();
        Call newCall = okHttpClient.newCall(request);
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onLoaded(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.code() == 200){
                    Document document = Jsoup.parse(response.body().string(),"");
                    Element first = document.getElementsByClass("content").first();
                    // http://bbs.csdn.net/topics/390844510
                    Elements ele_Img = first.getElementsByTag("img");
                    if (ele_Img.size() != 0){
                        for (Element e_Img : ele_Img) {
                            e_Img.attr("style", "width:100%");
                        }
                    }

                    ArticleDetail articleDetail = new ArticleDetail();
                    articleDetail.setArticleId(article.getArticleId());
                    articleDetail.setArticleContent(first.html());
                    callBack.onLoaded(articleDetail);
                }else {
                    callBack.onLoaded(null);
                }
            }
        });
    }
}


