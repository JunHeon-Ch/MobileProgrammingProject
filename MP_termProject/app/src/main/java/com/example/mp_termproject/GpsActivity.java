package com.example.mp_termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GpsActivity extends AppCompatActivity {

//    TextView resultTextView;
//    ToggleButton gpsToggleButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gps);
//
//        // Location 제공자에서 정보를 얻어오기(GPS)
//        // 1. Location을 사용하기 위한 권한을 얻어와야한다 AndroidManifest.xml
//        //     ACCESS_FINE_LOCATION : NETWORK_PROVIDER, GPS_PROVIDER
//        //     ACCESS_COARSE_LOCATION : NETWORK_PROVIDER
//        // 2. LocationManager 를 통해서 원하는 제공자의 리스너 등록
//        // 3. GPS 는 에뮬레이터에서는 기본적으로 동작하지 않는다
//        // 4. 실내에서는 GPS_PROVIDER 를 요청해도 응답이 없다.  특별한 처리를 안하면 아무리 시간이 지나도
//        //    응답이 없다.
//        //    해결방법은
//        //     ① 타이머를 설정하여 GPS_PROVIDER 에서 일정시간 응답이 없는 경우 NETWORK_PROVIDER로 전환
//        //     ② 혹은, 둘다 한꺼번헤 호출하여 들어오는 값을 사용하는 방식.
//
//        resultTextView = findViewById(R.id.resultTextView);
//        resultTextView.setText("위치정보 미수신중");
//
//        gpsToggleButton = findViewById(R.id.gpsToggleButton);
//
//        // LocationManager 객체를 얻어온다
//        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        gpsToggleButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                try {
//                    if (gpsToggleButton.isChecked()) {
//                        gpsToggleButton.setText("수신중..");
//
//                        // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치 제공자
//                                100,// 통지사이의 최소 시간간격 (miliSecond)
//                                1,// 통지사이의 최소 변경거리 (m)
//                                mLocationListener);
//
//                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치 제공자
//                                100,// 통지사이의 최소 시간간격 (miliSecond)
//                                1,// 통지사이의 최소 변경거리 (m)
//                                mLocationListener);
//                    }
//                    else{
//                        resultTextView.setText("위치정보 미수신중");
//                        locationManager.removeUpdates(mLocationListener);
//                    }
//                }catch (SecurityException exception){
//
//                }
//            }
//        });
//    } // end of onCreate
//
//    public final LocationListener mLocationListener = new LocationListener() {
//
//        @Override
//        public void onLocationChanged(Location location) {
////      여기서 위치값이 갱신되면 이벤트가 발생한다.
////      값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
//
//            Log.d("test", "onLocationChanged, location: " + location);
//            double longitude = location.getLongitude(); // 경도
//            double latitude = location.getLatitude(); // 위도
//            double altitude = location.getAltitude(); // 고도
//            float accuracy = location.getAccuracy(); // 정확도
//            String provider = location.getProvider(); // 위치제공자
//            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
//            //Network 위치제공자에 의한 위치변화
//            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
//
//            resultTextView.setText("위치정보: " + provider + "\n위도: " + latitude + "\n경도: "
//                    + longitude + "\n고도: " + altitude + "\n정확도: " + accuracy);
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            // Disable시
//            Log.d("test", "onProviderDisabled, provider:" + provider);
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//            // Enabled시
//            Log.d("test", "onProviderEnabled, provider:" + provider);
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//            // 변경시
//            Log.d("test", "onStatusChanged, provider:" + provider);
//        }
//    };
}
