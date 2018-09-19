package com.example.wdy.pyramiddoc;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class GuowangActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "Demo";
    private static final int UPDATE_TEXT=1;
    public static String oldersID=JinriActivity.oldersID;
    public static String zonghexinxit;
    private String[] bloodls = new String[7];
    private String[] bloodhs = new String[7];
    private int[] bloodl = new int[7];
    private int[] bloodh = new int[7];

    public static Info2 info =new Info2("Blood_p1","Blood_p2","Temp");
    private List<Info2> lists = new ArrayList<>();

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_TEXT:
                    for(int i=0;i<7;i++){
                        bloodh[i]=0;
                        bloodl[i]=0;
                        if(lists.size()>i) {
                            bloodhs[i] = lists.get(i).getBlood_p1();
                            bloodls[i] = lists.get(i).getBlood_p2();
                        }
                        else bloodhs[i]=bloodls[i]=null;
                        if(bloodhs[i]!=null&&bloodhs[i]!="")bloodh[i]=Integer.parseInt(bloodhs[i]);
                        if(bloodls[i]!=null&&bloodls[i]!="")bloodl[i]=Integer.parseInt(bloodls[i]);
                    }

                    LineChart lineChart =findViewById(R.id.lineChart);//创建描述信息
                    Description description = new Description();
                    description.setText("最近7天血压情况");
                    description.setTextColor(Color.GRAY);
                    description.setTextSize(12);
                    description.setPosition(570f,750f);
                    lineChart.setDescription(description);//设置图表描述信息
                    lineChart.setNoDataText("无数据");//没有数据时显示的文字
                    lineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
                    lineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
                    lineChart.setDrawBorders(false);//禁止绘制图表边框的线

                    //lineChart.setBorderColor(); //设置 chart 边框线的颜色。
                    //lineChart.setBorderWidth(); //设置 chart 边界线的宽度，单位 dp。
                    //lineChart.setLogEnabled(true);//打印日志
                    //lineChart.notifyDataSetChanged();//刷新数据
                    //lineChart.invalidate();//重绘


                    /**
                     * Entry 坐标点对象  构造函数 第一个参数为x点坐标 第二个为y点
                     */
                    ArrayList<Entry> values1 = new ArrayList<>();
                    ArrayList<Entry> values2 = new ArrayList<>();
                    ArrayList<Entry> values3 = new ArrayList<>();

                    values1.add(new Entry(1, bloodh[0]));
                    values1.add(new Entry(2, bloodh[1]));
                    values1.add(new Entry(3, bloodh[2]));
                    values1.add(new Entry(4, bloodh[3]));
                    values1.add(new Entry(5, bloodh[4]));
                    values1.add(new Entry(6, bloodh[5]));
                    values1.add(new Entry(7, bloodh[6]));

                    values2.add(new Entry(1, bloodl[0]));
                    values2.add(new Entry(2, bloodl[1]));
                    values2.add(new Entry(3, bloodl[2]));
                    values2.add(new Entry(4, bloodl[3]));
                    values2.add(new Entry(5, bloodl[4]));
                    values2.add(new Entry(6, bloodl[5]));
                    values2.add(new Entry(7, bloodl[6]));

                    //LineDataSet每一个对象就是一条连接线
                    LineDataSet set1;
                    LineDataSet set2;


                    //判断图表中原来是否有数据
                    if (lineChart.getData() != null &&
                            lineChart.getData().getDataSetCount() > 0) {
                        //获取数据1
                        set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
                        set1.setValues(values1);
                        set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
                        set2.setValues(values2);

                        //刷新数据
                        lineChart.getData().notifyDataChanged();
                        lineChart.notifyDataSetChanged();
                    } else {
                        //设置数据1  参数1：数据源 参数2：图例名称
                        set1 = new LineDataSet(values1, "收缩压/mmHg");
                        set1.setColor(Color.GRAY);
                        set1.setCircleColor(Color.GRAY);
                        set1.setLineWidth(1f);//设置线宽
                        set1.setCircleRadius(3f);//设置焦点圆心的大小
                        set1.setValueTextSize(9f);//设置显示值的文字大小
                        set1.setDrawFilled(false);//设置禁用范围背景填充

                        //格式化显示数据
                        final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
                        set1.setValueFormatter(new IValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                                return mFormat.format(value);
                            }
                        });

                        if (Utils.getSDKInt() >= 18) {
                            // fill drawable only supported on api level 18 and above /**
                            Drawable drawable = ContextCompat.getDrawable(GuowangActivity.this, R.drawable.ic_launcher_background);
                            set1.setFillDrawable(drawable);//设置范围背景填充
                        } else {
                            set1.setFillColor(Color.BLACK);
                        }

                        //设置数据2
                        set2 = new LineDataSet(values2, "舒张压/mmHg");
                        set2.setColor(0xFFC0D9D9);
                        set2.setCircleColor(0xFFC0D9D9);
                        set2.setLineWidth(1f);
                        set2.setCircleRadius(3f);
                        set2.setValueTextSize(9f);



                        //保存LineDataSet集合
                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1); // add the datasets
                        dataSets.add(set2);
                        //创建LineData对象 属于LineChart折线图的数据集合
                        LineData data = new LineData(dataSets);
                        // 添加到图表中
                        lineChart.setData(data);
                        //绘制图表
                        lineChart.invalidate();
                    }

                    //设置图表的x轴与y轴属性
                    //获取此图表的x轴
                    XAxis xAxis = lineChart.getXAxis();
                    xAxis.setEnabled(false);//设置轴启用或禁用 如果禁用以下的设置全部不生效
                    xAxis.setDrawAxisLine(true);//是否绘制轴线
                    xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
                    xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
                    //xAxis.setTextSize(20f);//设置字体
                    //xAxis.setTextColor(Color.BLACK);//设置字体颜色
                    //设置竖线的显示样式为虚线
                    //lineLength控制虚线段的长度
                    //spaceLength控制线之间的空间
                    xAxis.enableGridDashedLine(10f, 10f, 0f);
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值
//        xAxis.setAxisMaximum(10f);//设置最大值
                    xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
                    xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角度
//        设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
//        xAxis.setLabelCount(10);
//        xAxis.setTextColor(Color.BLUE);//设置轴标签的颜色
//        xAxis.setTextSize(24f);//设置轴标签的大小
//        xAxis.setGridLineWidth(10f);//设置竖线大小
//        xAxis.setGridColor(Color.RED);//设置竖线颜色
//        xAxis.setAxisLineColor(Color.GREEN);//设置x轴线颜色
//        xAxis.setAxisLineWidth(5f);//设置x轴线宽度
//        xAxis.setValueFormatter();//格式化x轴标签显示字符



                    /**
                     * Y轴默认显示左右两个轴线
                     */
                    //获取右边的轴线
                    YAxis rightAxis=lineChart.getAxisRight();
                    //设置图表右边的y轴禁用
                    rightAxis.setEnabled(false);
                    //获取左边的轴线
                    YAxis leftAxis = lineChart.getAxisLeft();
                    //设置网格线为虚线效果
                    leftAxis.enableGridDashedLine(10f, 10f, 0f);
                    //是否绘制0所在的网格线
                    leftAxis.setDrawZeroLine(false);

                    //设置与图表的交互
                    lineChart.setTouchEnabled(true); // 设置是否可以触摸
                    lineChart.setDragEnabled(true);// 是否可以拖拽
                    lineChart.setScaleEnabled(false);// 是否可以缩放 x和y轴, 默认是true
                    lineChart.setScaleXEnabled(true); //是否可以缩放 仅x轴
                    lineChart.setScaleYEnabled(true); //是否可以缩放 仅y轴
                    lineChart.setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
                    lineChart.setDoubleTapToZoomEnabled(true);//设置是否可以通过双击屏幕放大图表。默认是true
                    lineChart.setHighlightPerDragEnabled(true);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
                    lineChart.setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
                    lineChart.setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。

                    //设置图例
                    Legend l = lineChart.getLegend();//图例
                    l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);//设置图例的位置
                    l.setTextSize(10f);//设置文字大小
                    l.setForm(Legend.LegendForm.CIRCLE);//正方形，圆形或线
                    l.setFormSize(10f); // 设置Form的大小
                    l.setWordWrapEnabled(true);//是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
                    l.setFormLineWidth(10f);//设置Form的宽度
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guowang);
        TextView zonghexinxi=findViewById(R.id.zonghexinxi);
        zonghexinxi.setText(zonghexinxit);

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                if(MainActivity.conn!=null){
                    String sql1="SELECT * FROM import_info where date = (select date_add(curdate(),interval -6 day)) and ID = '" + oldersID+"'";
                    String sql2="SELECT * FROM import_info where date = (select date_add(curdate(),interval -5 day)) and ID = '" + oldersID+"'";
                    String sql3="SELECT * FROM import_info where date = (select date_add(curdate(),interval -4 day)) and ID = '" + oldersID+"'";
                    String sql4="SELECT * FROM import_info where date = (select date_add(curdate(),interval -3 day)) and ID = '" + oldersID+"'";
                    String sql5="SELECT * FROM import_info where date = (select date_add(curdate(),interval -2 day)) and ID = '" + oldersID+"'";
                    String sql6="SELECT * FROM import_info where date = (select date_add(curdate(),interval -1 day)) and ID = '" + oldersID+"'";
                    String sql7="SELECT * FROM import_info where date = curdate() and ID = '" + oldersID+"'";
                    try {
                        java.sql.Statement statement = MainActivity.conn.createStatement();
                        ResultSet rSet1 = statement.executeQuery(sql1);
                        while (rSet1.next()) {
                            info.setBlood_p1(rSet1.getString("Blood_p1"));
                            info.setBlood_p2(rSet1.getString("Blood_p2"));
                            info.setTemp(rSet1.getString("Temp"));
                            Info2 infos = new Info2(info.getBlood_p1(), info.getBlood_p2(), info.getTemp());
                            lists.add(infos);
                            rSet1.close();
                        }
                    }
                        catch (SQLException e){
                            Log.e(ACTIVITY_TAG,"createStatement error1"+e);
                        }
                        try {
                            java.sql.Statement statement1 = MainActivity.conn.createStatement();
                            ResultSet rSet2 = statement1.executeQuery(sql2);
                            while (rSet2.next()) {
                                info.setBlood_p1(rSet2.getString("Blood_p1"));
                                info.setBlood_p2(rSet2.getString("Blood_p2"));
                                info.setTemp(rSet2.getString("Temp"));
                                Info2 infos = new Info2(info.getBlood_p1(), info.getBlood_p2(), info.getTemp());
                                lists.add(infos);
                                rSet2.close();
                            }
                        }
                        catch (SQLException e){
                            Log.e(ACTIVITY_TAG,"createStatement error2"+e);
                        }
                        try {
                            java.sql.Statement statement2 = MainActivity.conn.createStatement();
                            ResultSet rSet3 = statement2.executeQuery(sql3);
                            while (rSet3.next()) {
                                info.setBlood_p1(rSet3.getString("Blood_p1"));
                                info.setBlood_p2(rSet3.getString("Blood_p2"));
                                info.setTemp(rSet3.getString("Temp"));
                                Info2 infos = new Info2(info.getBlood_p1(), info.getBlood_p2(), info.getTemp());
                                lists.add(infos);
                                rSet3.close();
                            }
                        }
                        catch (SQLException e){
                            Log.e(ACTIVITY_TAG,"createStatement error1"+e);
                        }

                        try {
                            java.sql.Statement statement3 = MainActivity.conn.createStatement();
                            ResultSet rSet4 = statement3.executeQuery(sql4);
                            while (rSet4.next()) {
                                info.setBlood_p1(rSet4.getString("Blood_p1"));
                                info.setBlood_p2(rSet4.getString("Blood_p2"));
                                info.setTemp(rSet4.getString("Temp"));
                                Info2 infos = new Info2(info.getBlood_p1(), info.getBlood_p2(), info.getTemp());
                                lists.add(infos);
                                rSet4.close();
                            }
                        }
                        catch (SQLException e){
                            Log.e(ACTIVITY_TAG,"createStatement error1"+e);
                        }

                        try {
                            java.sql.Statement statement4 = MainActivity.conn.createStatement();
                            ResultSet rSet5 = statement4.executeQuery(sql5);
                            while (rSet5.next()) {
                                info.setBlood_p1(rSet5.getString("Blood_p1"));
                                info.setBlood_p2(rSet5.getString("Blood_p2"));
                                info.setTemp(rSet5.getString("Temp"));
                                Info2 infos = new Info2(info.getBlood_p1(), info.getBlood_p2(), info.getTemp());
                                lists.add(infos);
                                rSet5.close();
                            }
                        }catch (SQLException e){
                            Log.e(ACTIVITY_TAG,"createStatement error1"+e);
                        }
                        try {

                            java.sql.Statement statement5 = MainActivity.conn.createStatement();
                            ResultSet rSet6 = statement5.executeQuery(sql6);
                            while (rSet6.next()) {
                                info.setBlood_p1(rSet6.getString("Blood_p1"));
                                info.setBlood_p2(rSet6.getString("Blood_p2"));
                                info.setTemp(rSet6.getString("Temp"));
                                Info2 infos = new Info2(info.getBlood_p1(), info.getBlood_p2(), info.getTemp());
                                lists.add(infos);
                                rSet6.close();
                            }
                        }catch (SQLException e){
                            Log.e(ACTIVITY_TAG,"createStatement error1"+e);
                        }
                        try{
                        java.sql.Statement statement6=MainActivity.conn.createStatement();
                        ResultSet rSet7=statement6.executeQuery(sql7);
                        while(rSet7.next()){
                            info.setBlood_p1(rSet7.getString("Blood_p1"));
                            info.setBlood_p2(rSet7.getString("Blood_p2"));
                            info.setTemp(rSet7.getString("Temp"));
                            Info2 infos = new Info2(info.getBlood_p1(),info.getBlood_p2(),info.getTemp());
                            lists.add(infos);
                            rSet7.close();
                        }
                    }catch (SQLException e){
                            Log.e(ACTIVITY_TAG,"createStatement error1"+e);
                        }

                    Message message=new Message();
                    message.what=UPDATE_TEXT;
                    handler.sendMessage(message);

                }

            }
        });
        thread.start();

    }

    public void back_click(View view) {
        Intent intent=new Intent(GuowangActivity.this,JinriActivity.class);
        startActivity(intent);
    }
}


