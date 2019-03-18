package com.hao.mivoice.bluetooth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hao.mivoice.MI2App;
import com.hao.mivoice.R;

import java.util.List;

public class ShowBlueToothAdapter extends BaseAdapter {

    public void addSelect() {
        if (select < blueToothDateList.size()) {
            select++;
        }
        notifyDataSetChanged();
    }

    public void reduceSelect() {
        if (select > 0) {
            select--;
        }
        notifyDataSetChanged();
    }

    public int getSelect() {
        return select;
    }

    private int select = 0;

    List<BlueToothDate> blueToothDateList;

    public void setDate(List<BlueToothDate> blueToothDateList) {
        this.blueToothDateList = blueToothDateList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return blueToothDateList == null ? 0 : blueToothDateList.size();
    }

    @Override
    public BlueToothDate getItem(int position) {
        return blueToothDateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(MI2App.getInstance().getApplicationContext()).inflate(R.layout.blue_tooth_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.blue_tooth_name);
            viewHolder.item = convertView.findViewById(R.id.item);
            viewHolder.address = convertView.findViewById(R.id.blue_tooth_address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == select) {
            viewHolder.item.setBackgroundResource(R.color.black33);
        } else {
            viewHolder.item.setBackgroundResource(R.color.white);
        }
        viewHolder.name.setText("蓝牙名称：" + blueToothDateList.get(position).device.getName());
        viewHolder.address.setText("蓝牙地址：" + blueToothDateList.get(position).device.getAddress());
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView address;
        LinearLayout item;
    }
}
