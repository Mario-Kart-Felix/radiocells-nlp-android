/*
	Radiobeacon - Openbmap wifi and cell logger
    Copyright (C) 2013  wish7

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.radiocells.unifiedNlp;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import org.radiocells.unifiedNlp.utils.CatalogDownload;
import org.radiocells.unifiedNlp.utils.ICatalogsListAdapterListener;

public class DialogPreferenceCatalogsListAdapter extends BaseExpandableListAdapter {

    private final SparseArray<org.radiocells.unifiedNlp.DialogPreferenceCatalogsGroup> mGroups;
    private final ICatalogsListAdapterListener mCallback;
    private LayoutInflater mInflater;
    private Context mContext;

    DialogPreferenceCatalogsListAdapter(Context context, SparseArray<DialogPreferenceCatalogsGroup> groups, ICatalogsListAdapterListener callback) {
        mGroups = groups;
        mContext = context;
        mInflater =  LayoutInflater.from(mContext);
        mCallback = callback;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mGroups.get(groupPosition) != null) {
            return mGroups.get(groupPosition).children.get(childPosition);
        } else {
            return null;
        }
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final CatalogDownload children = (CatalogDownload) getChild(groupPosition, childPosition);
        TextView text;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dialogpreference_catalogs_detail, null);
        }
        text = convertView.findViewById(R.id.textView1);
        text.setText(children.getTitle());
        text.setTag(children.getUrl());
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onItemClicked(children);
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mGroups.get(groupPosition) != null) {
            return mGroups.get(groupPosition).children.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dialogpreference_catalogs_group, null);
        }
        DialogPreferenceCatalogsGroup group = (DialogPreferenceCatalogsGroup) getGroup(groupPosition);
        if (group != null) {
            ((CheckedTextView) convertView).setText(group.string);
        } else {
            ((CheckedTextView) convertView).setText(R.string.server_parsing_error);
        }
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}