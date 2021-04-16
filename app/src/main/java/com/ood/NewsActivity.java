package com.ood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DrawerLayout drawerLayout;
    private ListView listLeftDrawer;
    private ArrayList<ClipData.Item> menuLists;
    private MyAdapter<ClipData.Item> myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);
//                if(true) {
//                    System.out.println("=====================");
//                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
//                    NewsActivity.super.onResume();
//                    //onCreate(null);
//                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });



        String[] names = {"CNN News", "Fox News", "Yahoo News", "New York Times", "Google News"};
//        String[] says = new String[]{"COvi"};
        int[] imgIds = new int[]{R.mipmap.cnn, R.mipmap.foxnews, R.mipmap.yahoonews, R.mipmap.newyork, R.mipmap.googlenews};

        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("touxiang", imgIds[i]);
            showitem.put("name", names[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        //SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.list_item, new String[]{"touxiang", "name", "says"}, new int[]{R.id.imgtou, R.id.name, R.id.says});
        MyAdapter<Map<String, Object>> myAdapter = new MyAdapter<Map<String, Object>>((ArrayList) listitem, R.layout.list_item) {
            @Override
            public void bindView(ViewHolder holder, Map<String, Object> obj) {
                holder.setText(R.id.name, (CharSequence) obj.get("name"));
                holder.setImageResource(R.id.imgtou, (int) obj.get("touxiang"));
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_test);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Uri uri = Uri.parse("https://www.cnn.com/health");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case 1:
                Uri uri2 = Uri.parse("https://www.foxnews.com/health");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
                break;
            case 2:
                Uri uri3 = Uri.parse("https://news.yahoo.com/coronavirus");
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
                startActivity(intent3);
                break;
            case 3:
                Uri uri4 = Uri.parse("https://www.nytimes.com/section/health");
                Intent intent4 = new Intent(Intent.ACTION_VIEW, uri4);
                startActivity(intent4);
                break;
            case 4:
                Uri uri5 = Uri.parse("https://news.google.com/topics/CAAqBwgKMJy5lwswj-KuAw?hl=en-US&gl=US&ceid=US%3Aen");
                Intent intent5 = new Intent(Intent.ACTION_VIEW, uri5);
                startActivity(intent5);
                break;
        }
    }
}
//
// This is a test comment by CLH
// This is a test comment by Jin
// This is a test comment by limin

//package com.ood;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Message;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.scwang.smart.refresh.footer.ClassicsFooter;
//import com.scwang.smart.refresh.header.ClassicsHeader;
//import com.scwang.smart.refresh.layout.api.RefreshLayout;
//import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
//import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.logging.Handler;
//
//import static java.util.logging.Logger.global;
//
//
//public class NewsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news1);
//        String[] titles = {"CDC vaccine advisers put off decision on Johnson & Johnson Covid-19 vaccine",
//                "C.D.C. Panel Keeps Pause on Use of J&J Vaccine, Citing Need to Assess Potential Risks",
//                "How does Johnson & Johnson's COVID-19 vaccine compare to Moderna, Pfizer?",
//                "Novartis agrees to make ingredients Roche's Actemra for COVID-19 patients"};
//        int[] imgs = {R.mipmap.cnnnewspic, R.mipmap.foxnewspic, R.mipmap.yahoonewspic, R.mipmap.newyorktimespic};
//
//        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < titles.length; i++) {
//            Map<String, Object> showitem = new HashMap<String, Object>();
//            showitem.put("touxiang", imgs[i]);
//            showitem.put("name", titles[i]);
//            listitem.add(showitem);
//        }
//
//        //创建一个simpleAdapter
//        //SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.list_item, new String[]{"touxiang", "name", "says"}, new int[]{R.id.imgtou, R.id.name, R.id.says});
//        MyAdapter<Map<String, Object>> myAdapter1 = new MyAdapter<Map<String, Object>>((ArrayList) listitem, R.layout.list_news) {
//            @Override
//            public void bindView(ViewHolder holder, Map<String, Object> obj) {
//                holder.setText(R.id.name, (CharSequence) obj.get("name"));
//                holder.setImageResource(R.id.imgtou, (int) obj.get("touxiang"));
//            }
//        };
//        ListView listView = (ListView) findViewById(R.id.list_test);
//        listView.setAdapter(myAdapter1);
//        listView.setOnItemClickListener(this);
////        try {
////            fetchData();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
////        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
////        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
////        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
////            @Override
////            public void onRefresh(RefreshLayout refreshlayout) {
////                if(getNewData()) {
////                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
////                    NewsActivity.super.onResume();
////                    //onCreate(null);
////                }
////            }
////        });
////        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
////            @Override
////            public void onLoadMore(RefreshLayout refreshlayout) {
////                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
////            }
////        });
////    }
////
////    private boolean getNewData() {
////        return true;
////    }
////
////
////    public void fetchData() throws IOException {
//////        if(!isNetworkAvailable(this))
//////            return;
//////        System.out.println("go into fetch===========");
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    String url = "https://www.cnn.com/health";
////                    Connection conn = Jsoup.connect(url);
////                    // 修改http包中的header,伪装成浏览器进行抓取
////                    conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
////                    Document doc = null;
////                    doc = conn.get();
////                    System.out.println("test=============2");
////                    // 获取tbody元素下的所有tr元素
////                    Elements elements = doc.getElementsByClass("media");
////                    for(Element element:elements) {
////
////                    }
//////                    Elements elements = doc.select("tbody tr");
//////                    for(Element element : elements) {
//////                        String companyName = element.getElementsByTag("company").text();
//////                        String time = element.select("td.text-center").first().text();
//////                        String address = element.getElementsByClass("preach-tbody-addre").text();
//////
//////                        System.out.println("公司："+companyName);
//////                        System.out.println("宣讲时间："+time);
//////                        System.out.println("宣讲学校：华中科技大学");
//////                        System.out.println("具体地点："+address);
//////                        System.out.println("---------------------------------");
//////                        break;
//////                    }
////                } catch (IOException e) {
////                    //Toast.makeText(NewsActivity.this,"无法连接网络",Toast.LENGTH_SHORT).show();
////                    e.printStackTrace();
////                }
////            }
////        }).start();
//
//
//    }
//
//
//    public static boolean isNetworkAvailable(Activity activity) {
//        Context context = activity.getApplicationContext();
//        ConnectivityManager cm = (ConnectivityManager)
//                context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        if (cm == null)
//            return false;
//        else {   // 获取所有NetworkInfo对象
//            NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
//            if (networkInfo != null && networkInfo.length > 0) {
//                for (int i = 0; i < networkInfo.length; i++)
//                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
//                        return true;  // 存在可用的网络连接
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        switch (position) {
//            case 0:
//                Uri uri = Uri.parse("https://www.cnn.com/health");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//                break;
//            case 1:
//                Uri uri2 = Uri.parse("https://www.foxnews.com/health");
//                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
//                startActivity(intent2);
//                break;
//            case 2:
//                Uri uri3 = Uri.parse("https://news.yahoo.com/coronavirus");
//                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
//                startActivity(intent3);
//                break;
//            case 3:
//                Uri uri4 = Uri.parse("https://www.nytimes.com/section/health");
//                Intent intent4 = new Intent(Intent.ACTION_VIEW, uri4);
//                startActivity(intent4);
//                break;
////            case 4:
////                Uri uri5 = Uri.parse("https://news.google.com/topics/CAAqBwgKMJy5lwswj-KuAw?hl=en-US&gl=US&ceid=US%3Aen");
////                Intent intent5 = new Intent(Intent.ACTION_VIEW, uri5);
////                startActivity(intent5);
////                break;
//        }
//    }
//}

