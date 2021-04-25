package com.example.dea.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.evrencoskun.tableview.TableView;
import com.example.dea.R;
import com.example.dea.adapter.TableAdapter;
import com.example.dea.api.data.DataPreparation;
import com.example.dea.api.javaScript.WebClient;
import com.example.dea.calculator.IO_BCCModel;
import com.example.dea.calculator.OO_BCCModel;
import com.example.dea.entity.DMU;
import com.example.dea.ui.TableViewAttributes;
import com.example.dea.util.SharedPreferenceManager;

import java.util.ArrayList;


public class OO_BCCModelViewFragment extends Fragment {
    private ArrayList<DMU> dmuArrayList;
    private ArrayList<DMU> arrayList;
    private final static String ARG_PARAM1="array_list_name";
    private WebView webView;
    private SharedPreferenceManager sharedPreferenceManager;
    public OO_BCCModelViewFragment() {
        // Required empty public constructor
    }

    public static OO_BCCModelViewFragment newInstance(ArrayList<DMU> dmuArrayList) {
        OO_BCCModelViewFragment fragment = new OO_BCCModelViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,dmuArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arrayList =(ArrayList<DMU>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_o_o__b_c_c_model_view, container, false);
        sharedPreferenceManager=new SharedPreferenceManager(getContext());
        setView(root);
        calculateIO_BCCModel();
        setTable(root);
        drawGraph();
        return root;
    }
    private void setView(View root){
        webView = (WebView) root.findViewById(R.id.web_view_OO);
    }
    private void calculateIO_BCCModel(){
        OO_BCCModel oo_bccModel=new OO_BCCModel(getContext(),arrayList);
        dmuArrayList=oo_bccModel.calculateOO_BCModel();
    }
    private void setTable(View root){
        TableView tableView = new TableView(root.getContext());
        tableView=root.findViewById(R.id.table_OO);
        TableAdapter adapter = new TableAdapter(root.getContext());
        tableView.setAdapter(adapter);
        // Let's set datas of the TableView on the Adapter
        TableViewAttributes attributes=new TableViewAttributes(getContext(),dmuArrayList);
        adapter.setAllItems(attributes.getColumnHeader(root.getContext()), attributes.getRowHeader(root.getContext()), attributes.getCell(root.getContext()));
    }
    private void drawGraph(){
        setWebView();
    }
    private void setWebView(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setAllowFileAccess(true);
        }
        webView.requestFocusFromTouch();
        webView.clearCache(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.clearCache(true);
        webView.clearHistory();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        WebClient client=new WebClient(getContext());
        webView.addJavascriptInterface(client,"android");
        webView.loadUrl(prepareData(client));
    }
    private String prepareData(WebClient client){
        DataPreparation data=new DataPreparation(getContext(),dmuArrayList);
        return data.checkDataType(client,true);
    }

}
