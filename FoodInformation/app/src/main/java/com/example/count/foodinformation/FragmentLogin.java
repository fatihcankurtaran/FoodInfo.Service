package com.example.count.foodinformation;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import Model.CreateUserClass;
import Model.LoginClass;
import Model.UserDTO;
import Remote.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment implements View.OnClickListener {

    private Service service;
    private EditText txID,txPW;
    private TextView txStatus;
    private CheckBox checkBox;
    public static int UserID;
    public static boolean isAdmin,isModerator;
    public boolean addproduct = false;
    public boolean addcontent = false;
    public static Bundle bundle;
    FragmentTransaction fragmentTransaction;
    public FragmentLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_login, container, false);
        fragmentTransaction  =  getActivity().getSupportFragmentManager().beginTransaction();
        bundle = new Bundle();
        service = Common.GetService();
        txID = view.findViewById(R.id.txID);
        txPW = view.findViewById(R.id.txPW);
        checkBox = view.findViewById(R.id.checkRemember);
        txStatus = view.findViewById(R.id.txStatus);
        view.findViewById(R.id.btnLogin).setOnClickListener(this);
        view.findViewById(R.id.btnGotoCreate).setOnClickListener(this);
        SharedPreferences prefs = getActivity().getSharedPreferences("FoodINFO", MODE_PRIVATE);
            String ID = prefs.getString("ID", "No name defined");
            String PW = prefs.getString("PW", "No name defined");
            if(!ID.equals("No name defined"))
            {
                txID.setText(ID);
                txPW.setText(PW);
            }
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLogin)
        {
            txStatus.setText("");
            LoginClass loginClass;
            if(txID.getText().toString().contains("@"))
                loginClass =  new LoginClass(txPW.getText().toString(),null,txID.getText().toString());
            else
                loginClass =  new LoginClass(txPW.getText().toString(),txID.getText().toString(),null);
            service.Login(loginClass).enqueue(new Callback<LoginClass>() {
                @Override
                public void onResponse(Call<LoginClass> call, Response<LoginClass> response) {
                    if(response.isSuccessful())
                    {
                        String UserName = response.body().getResult().getUsername();

                        isAdmin = response.body().getResult().isAdmin();
                        isModerator = response.body().getResult().isModerator();
                        if(isAdmin)
                        {
                            MainActivity.product.setVisible(true);
                            MainActivity.users.setVisible(true);
                        }
                        if(isModerator)
                        {
                            MainActivity.product.setVisible (true);
                           // MainActivity.Categorize.setVisible(true);
                        }
                        isModerator = response.body().getResult().isModerator();
                        service.GetUserDetailByUsername(new UserDTO(UserName)).enqueue(new Callback<UserDTO>() {
                            @Override
                            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                                if(response.isSuccessful())
                                {
                                     if(checkBox.isChecked())
                                    {
                                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("FoodINFO", MODE_PRIVATE).edit();
                                        editor.putString("ID", txID.getText().toString());
                                        editor.putString("PW", txPW.getText().toString());
                                        editor.apply();

                                    }
                                    UserID = response.body().getResult().Id;


                                    if(addproduct)
                                    {
                                        bundle.putString("BARCODE",getArguments().getString("BARCODE"));
                                        addproduct=false;
                                        FragmentAddProduct fragmentAddProduct =  new FragmentAddProduct();
                                        fragmentAddProduct.setArguments(bundle);
                                        setFragment(fragmentAddProduct);//setFragment(fragmentProfile);
                                    } else if (addcontent)
                                    {
                                        bundle.putString("BARCODE",getArguments().getString("BARCODE"));
                                        addcontent=false;
                                        FragmentAddContent fragmentAddContent =  new FragmentAddContent();
                                        fragmentAddContent.setArguments(bundle);
                                        setFragment(fragmentAddContent);
                                    } else {

                                        bundle.putString("NAME", response.body().getResult().getName());
                                        bundle.putString("SURNAME", response.body().getResult().getSurname());
                                        bundle.putString("USERNAME", response.body().getResult().getUsername());
                                        bundle.putString("EMAIL", response.body().getResult().getEmail());
                                        MainActivity.mainActivity.fragmentProfile.setArguments(bundle);
                                        if(isAdmin)
                                            setFragment(new FragmentAllUsers());//setFragment(fragmentProfile);
                                        else if(isModerator)
                                            setFragment(new FragmentAllProduct());//setFragment(fragmentProfile);
                                        else
                                            setFragment(MainActivity.mainActivity.fragmentProfile);//setFragment(fragmentProfile);


                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDTO> call, Throwable t) {

                            }
                        });

                    }
                    else
                    {
                        Gson gson = new Gson();
                        try {
                            txStatus.setTextColor(Color.RED);
                            txStatus.setVisibility(View.VISIBLE);
                            CreateUserClass r = gson.fromJson(response.errorBody().string(), CreateUserClass.class);
                            if(r != null)
                                txStatus.setText(MainActivity.Errors.get(r.getMessage()));
                            else
                                txStatus.setText("Unknown Error");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginClass> call, Throwable t) {
                    txStatus.setText("Fail");
                }
            });

        }
        else if(view.getId() == R.id.btnGotoCreate)
        {
            setFragment(new FragmentCreateUser());
        }
    }
    private void setFragment(Fragment fragment)
    {

        fragmentTransaction.replace(R.id.frame_layout,fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
