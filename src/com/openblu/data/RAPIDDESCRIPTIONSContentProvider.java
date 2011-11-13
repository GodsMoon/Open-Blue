/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *																			  *																			*
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 * 																			  *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */
package com.openblu.data;

import java.util.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.text.*;

import com.openblu.data.*;

public class RAPIDDESCRIPTIONSContentProvider extends ContentProvider {

	private SQLOpenHelper dbHelper;
	private static HashMap<String, String> RAPIDDESCRIPTIONS_PROJECTION_MAP;
	private static final String TABLE_NAME = "rapiddescriptions";
	private static final String AUTHORITY = "com.openblu.data.rapiddescriptionscontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri _ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase());
	public static final Uri RAPID_ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/rapid_id");
	public static final Uri SCORE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/score");
	public static final Uri DESCRIPTION_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/description");

	public static final String DEFAULT_SORT_ORDER = "_id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int RAPIDDESCRIPTIONS = 1;
	private static final int RAPIDDESCRIPTIONS__ID = 2;
	private static final int RAPIDDESCRIPTIONS_RAPID_ID = 3;
	private static final int RAPIDDESCRIPTIONS_SCORE = 4;
	private static final int RAPIDDESCRIPTIONS_DESCRIPTION = 5;

	// Content values keys (using column names)
	public static final String _ID = "_id";
	public static final String RAPID_ID = "Rapid_id";
	public static final String SCORE = "Score";
	public static final String DESCRIPTION = "Description";

	public boolean onCreate() {
		dbHelper = new SQLOpenHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case RAPIDDESCRIPTIONS:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(RAPIDDESCRIPTIONS_PROJECTION_MAP);
			break;
		case RAPIDDESCRIPTIONS__ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("_id=" + url.getPathSegments().get(1));
			break;
		case RAPIDDESCRIPTIONS_RAPID_ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("rapid_id='" + url.getPathSegments().get(2) + "'");
			break;
		case RAPIDDESCRIPTIONS_SCORE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("score='" + url.getPathSegments().get(2) + "'");
			break;
		case RAPIDDESCRIPTIONS_DESCRIPTION:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("description='" + url.getPathSegments().get(2) + "'");
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		String orderBy = "";
		if (TextUtils.isEmpty(sort)) {
			orderBy = DEFAULT_SORT_ORDER;
		} else {
			orderBy = sort;
		}
		Cursor c = qb.query(mDB, projection, selection, selectionArgs, null,
				null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	public String getType(Uri url) {
		switch (URL_MATCHER.match(url)) {
		case RAPIDDESCRIPTIONS:
			return "vnd.android.cursor.dir/vnd.com.openblu.data.rapiddescriptions";
		case RAPIDDESCRIPTIONS__ID:
			return "vnd.android.cursor.item/vnd.com.openblu.data.rapiddescriptions";
		case RAPIDDESCRIPTIONS_RAPID_ID:
			return "vnd.android.cursor.item/vnd.com.openblu.data.rapiddescriptions";
		case RAPIDDESCRIPTIONS_SCORE:
			return "vnd.android.cursor.item/vnd.com.openblu.data.rapiddescriptions";
		case RAPIDDESCRIPTIONS_DESCRIPTION:
			return "vnd.android.cursor.item/vnd.com.openblu.data.rapiddescriptions";

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
	}

	public Uri insert(Uri url, ContentValues initialValues) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		long rowID;
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		if (URL_MATCHER.match(url) != RAPIDDESCRIPTIONS) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("rapiddescriptions", "rapiddescriptions", values);
		if (rowID > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		throw new SQLException("Failed to insert row into " + url);
	}

	public int delete(Uri url, String where, String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case RAPIDDESCRIPTIONS:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case RAPIDDESCRIPTIONS__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.delete(TABLE_NAME,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case RAPIDDESCRIPTIONS_RAPID_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"rapid_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case RAPIDDESCRIPTIONS_SCORE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"score="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case RAPIDDESCRIPTIONS_DESCRIPTION:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"description="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case RAPIDDESCRIPTIONS:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case RAPIDDESCRIPTIONS__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.update(TABLE_NAME, values,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case RAPIDDESCRIPTIONS_RAPID_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"rapid_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case RAPIDDESCRIPTIONS_SCORE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"score="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case RAPIDDESCRIPTIONS_DESCRIPTION:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"description="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	static {
		URL_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(),
				RAPIDDESCRIPTIONS);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/#",
				RAPIDDESCRIPTIONS__ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/rapid_id"
				+ "/*", RAPIDDESCRIPTIONS_RAPID_ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/score"
				+ "/*", RAPIDDESCRIPTIONS_SCORE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/description"
				+ "/*", RAPIDDESCRIPTIONS_DESCRIPTION);

		RAPIDDESCRIPTIONS_PROJECTION_MAP = new HashMap<String, String>();
		RAPIDDESCRIPTIONS_PROJECTION_MAP.put(_ID, "_id");
		RAPIDDESCRIPTIONS_PROJECTION_MAP.put(RAPID_ID, "rapid_id");
		RAPIDDESCRIPTIONS_PROJECTION_MAP.put(SCORE, "score");
		RAPIDDESCRIPTIONS_PROJECTION_MAP.put(DESCRIPTION, "description");

	}
}
