package com.tanzoft.habarihub;

import java.io.IOException;
import com.tanzoft.habarihub.database.NewsSourceDatabaseOpenHelper;
import android.app.Application;

public class HabariHubApplication extends Application {

	public static NewsSourceDatabaseOpenHelper dbHelper;
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		dbHelper = new NewsSourceDatabaseOpenHelper(this);
		
		try {
			//copies the database from assets folder if its not present yet
			dbHelper.createEmptyDatabase();
		} catch (IOException e) {
			
		}
		
		

		/*
		 * 
		 //adds items to the database
		String name;
		String blogSite;
		
		name="Michuzi Blog";
		blogSite="http://www.issamichuzi.blogspot.com/feeds/posts/default?alt=rss";
		
		dbHelper.addBlog(new NewsSource(name, blogSite));
		
		name="MillardAyo";
		blogSite = "http://www.millardayo.com/feed/";
	
		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="8020Fashions Blog";
		
		blogSite = "http://www.8020fashionsblog.com/feed/";

		
		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="Cheka na Kitime";
		
		blogSite = "http://www.chekanakitime.blogspot.com/feeds/posts/default?alt=rss";

		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="DJ Choka";
		blogSite = "http://www.djchoka.blogspot.com/feeds/posts/default?alt=rss";

		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="East African Herald";
		blogSite = "http://www.eastafricaherald.com/feeds/posts/default?alt=rss";

		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="Fununu Blog";
		
		blogSite = "http://www.fununuhabarii.blogspot.com/feeds/posts/default?alt=rss";

		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="Gospel Kitaa";
		blogSite = "http://www.gospelkitaa.blogspot.com/feeds/posts/default?alt=rss";

		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="Kajuna Son";
		blogSite = "http://www.kajunason.blogspot.com/feeds/posts/default?alt=rss";

		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="Kijiwe cha Kitime";
		blogSite = "http://www.wanamuzikiwatanzania.blogspot.com/feeds/posts/default?alt=rss";

		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="Mpekuzi";
		
		blogSite = "http://www.freebongo.blogspot.com/feeds/posts/default?alt=rss";

		
		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="Soka in Bongo";
		
		blogSite = "http://www.sokainbongo.com/vikombe-vikubwa?format=feed&type=rss";

		
		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="Wapenda Soka";
		
		blogSite = "http://www.wapendasoka.blogspot.com/feeds/posts/default?alt=rss";

		
		dbHelper.addBlog(new NewsSource(name, blogSite));

		name="Bongo Electronics";
		
		blogSite = "http://www.bongoelectronics.com/feed/";

		
		dbHelper.addBlog(new NewsSource(name, blogSite));
		*/
		
		
		
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	
	

}
