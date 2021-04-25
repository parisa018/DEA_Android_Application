package com.example.dea.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.evrencoskun.tableview.TableView;
import com.example.dea.R;
import com.example.dea.adapter.TableAdapter;
import com.example.dea.api.data.DataPreparation;
import com.example.dea.api.javaScript.WebClient;
import com.example.dea.calculator.CCRModel;
import com.example.dea.entity.DMU;
import com.example.dea.ui.TableViewAttributes;
import com.example.dea.util.SharedPreferenceManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CCRModelViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CCRModelViewFragment extends Fragment {
    private ArrayList<DMU> dmuArrayList;
    private ArrayList<DMU> arrayList;
    private final static String ARG_PARAM1="array_list_name";
    private WebView webView;
    private SharedPreferenceManager sharedPreferenceManager;

    public CCRModelViewFragment() {
        // Required empty public constructor
    }
    public static CCRModelViewFragment newInstance(ArrayList<DMU> dmuArrayList) {
        CCRModelViewFragment fragment = new CCRModelViewFragment();
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
        View root=inflater.inflate(R.layout.fragment_c_c_r, container, false);
        sharedPreferenceManager=new SharedPreferenceManager(getContext());
        setView(root);
        calculateCCRModel();
        setTable(root);
        drawGraph(root);
        return root;
    }
    private void calculateCCRModel(){
        CCRModel ccrModel=new CCRModel(getContext(),arrayList);
        dmuArrayList=ccrModel.calculateCCRModel();
    }
    private void setView(View root){
        webView = (WebView) root.findViewById(R.id.web_view);
    }
    private void drawGraph(View root){
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
    private void setTable(View root){
        TableView tableView = new TableView(root.getContext());
        tableView=root.findViewById(R.id.table);
        TableAdapter adapter = new TableAdapter(root.getContext());
        tableView.setAdapter(adapter);
        // Let's set datas of the TableView on the Adapter
        TableViewAttributes attributes=new TableViewAttributes(getContext(),dmuArrayList);
        adapter.setAllItems(attributes.getColumnHeader(root.getContext()), attributes.getRowHeader(root.getContext()), attributes.getCell(root.getContext()));
    }






}

