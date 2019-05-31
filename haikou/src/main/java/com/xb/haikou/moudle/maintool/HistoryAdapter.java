package com.xb.haikou.moudle.maintool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xb.haikou.R;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.moudle.function.unionpay.entity.UnionPayEntity;
import com.xb.haikou.record.CardRecordEntity;
import com.xb.haikou.record.JTBscanRecord;
import com.xb.haikou.record.ScanRecordEntity;
import com.xb.haikou.util.DateUtil;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Holder> {
    int type = 0;//0 刷卡记录  1 腾讯二维码记录  2 银联记录   3支付宝二维码记录  4交通部二维码记录
    int page = 0;//当前显示的页数
    int maxPage = 0;//显示的最大页数

    Object list;
    Context context;

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    public void setType(int type) {
        page = 0;
        switch (type) {
            case 0:
                list = DBManager.checkCardRecord(true);
                break;
            case 1:
                list = DBManager.checkTXScanRecord(true);
                break;
            case 2:
                list = DBManager.checkUnionRecord(true);
                break;
            case 3:
                list = DBManager.checkALScanRecord(true);
                break;
            case 4:
                list = DBManager.checkJTBScan(true);
                break;
        }
        maxPage = ((List) list).size() / 10;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_layout, null);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder viewHolder, int i) {
        if (i == 0) {
            viewHolder.setDate("交易标示", "交易金额", "是否上传", "交易时间");
        } else {
            Object o = ((List) list).get(page * 10 + i - 1);
            switch (type) {
                case 0:
                    CardRecordEntity card = (CardRecordEntity) o;
                    viewHolder.setDate(card.getCardNo(), card.getFareAmt() + "", card.getIsUploade().equals("0") ? "未上传" : "已上传", DateUtil.getTimeStr(card.getTime()));
                    break;
                case 1:
                    ScanRecordEntity txScan = (ScanRecordEntity) o;
                    viewHolder.setDate(txScan.getOpen_id(), txScan.getPay_fee(), txScan.getUpState().equals("0") ? "未上传" : "已上传", DateUtil.getTimeStr(txScan.getCreatTime()));
                    break;
                case 2:
                    UnionPayEntity union = (UnionPayEntity) o;
                    viewHolder.setDate(union.getMainCardNo(), union.getPayFee(), union.getUpStatus().equals("0") ? "未上传" : "已上传", DateUtil.getTimeStr(union.getCreattime()));
                    break;
                case 3:
                    ScanRecordEntity alScan = (ScanRecordEntity) o;
                    viewHolder.setDate(alScan.getOpen_id(), alScan.getPay_fee(), alScan.getUpState().equals("0") ? "未上传" : "已上传", DateUtil.getTimeStr(alScan.getCreatTime()));
                    break;
                case 4:
                    JTBscanRecord jtBscanRecord = (JTBscanRecord) o;
                    viewHolder.setDate(jtBscanRecord.getTerseno(), jtBscanRecord.getPayFee(), jtBscanRecord.getIsUpload().equals("0") ? "未上传" : "已上传", DateUtil.getTimeStr(jtBscanRecord.getCreatetime()));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 1;
        if (list == null || ((List) list).size() == 0) {
            count = 1;
        } else if (page < maxPage) {
            count = 10;
        } else {
            count = ((List) list).size() % 10;
        }
        return count;
    }

    class Holder extends RecyclerView.ViewHolder {
        View itemView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void setDate(String id, String price, String isupload, String tranTime) {
            ((TextView) itemView.findViewById(R.id.tran_id)).setText(id);
            ((TextView) itemView.findViewById(R.id.tran_price)).setText(price);
            ((TextView) itemView.findViewById(R.id.isupload)).setText(isupload);
            ((TextView) itemView.findViewById(R.id.tran_time)).setText(tranTime);
        }
    }

}
