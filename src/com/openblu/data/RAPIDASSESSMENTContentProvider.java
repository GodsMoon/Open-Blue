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

public class RAPIDASSESSMENTContentProvider extends ContentProvider {

	private SQLOpenHelper dbHelper;
	private static HashMap<String, String> RAPIDASSESSMENT_PROJECTION_MAP;
	private static final String TABLE_NAME = "rapidassessment";
	private static final String AUTHORITY = "com.openblu.data.rapidassessmentcontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri _ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase());
	public static final Uri CRITERIA_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/criteria");

	public static final String DEFAULT_SORT_ORDER = "_id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int RAPIDASSESSMENT = 1;
	private static final int RAPIDASSESSMENT__ID = 2;
	private static final int RAPIDASSESSMENT_CRITERIA = 3;

	// Content values keys (using column names)
	public static final String _ID = "_id";
	public static final String CRITERIA = "Criteria";

	public boolean onCreate() {
		dbHelper = new SQLOpenHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case RAPIDASSESSMENT:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(RAPIDASSESSMENT_PROJECTION_MAP);
			break;
		case RAPIDASSESSMENT__ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("_id=" + url.getPathSegments().get(1));
			break;
		case RAPIDASSESSMENT_CRITERIA:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("criteria='" + url.getPathSegments().get(2) + "'");
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
		case RAPIDASSESSMENT:
			return "vnd.android.cursor.dir/vnd.com.openblu.data.rapidassessment";
		case RAPIDASSESSMENT__ID:
			return "vnd.android.cursor.item/vnd.com.openblu.data.rapidassessment";
		case RAPIDASSESSMENT_CRITERIA:
			return "vnd.android.cursor.item/vnd.com.openblu.data.rapidassessment";

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
		if (URL_MATCHER.match(url) != RAPIDASSESSMENT) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("rapidassessment", "rapidassessment", values);
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
		case RAPIDASSESSMENT:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case RAPIDASSESSMENT__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.delete(TABLE_NAME,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case RAPIDASSESSMENT_CRITERIA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"criteria="
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
		case RAPIDASSESSMENT:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case RAPIDASSESSMENT__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.update(TABLE_NAME, values,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case RAPIDASSESSMENT_CRITERIA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"criteria="
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
		URL_MATCHER
				.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), RAPIDASSESSMENT);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/#",
				RAPIDASSESSMENT__ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/criteria"
				+ "/*", RAPIDASSESSMENT_CRITERIA);

		RAPIDASSESSMENT_PROJECTION_MAP = new HashMap<String, String>();
		RAPIDASSESSMENT_PROJECTION_MAP.put(_ID, "_id");
		RAPIDASSESSMENT_PROJECTION_MAP.put(CRITERIA, "criteria");

	}
}
