package com.ddebbie.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ddebbie.business.BusinessAddress;
import com.ddebbie.business.BusinessParcel;
import com.ddebbie.business.BusinessShippo;
import com.shippo.Shippo;
import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.shippo.model.RateCollection;
import com.shippo.model.Shipment;


@Service("ShipmentHandler")
public class ShipmentHandler {
  //@Value("${shippo_apitoken}")
  public static  String SHIPPO_APITOKEN="shippo_test_bb7014c4721061b5c2abfb37afbb8bf269c2ae58";

public RateCollection createShipment(BusinessShippo businessShippo) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, InterruptedException {
	
	if(businessShippo!=null){
				// replace with your Shippo Token
				// don't have one? get more info here (https://goshippo.com/docs/#overview)
				Shippo.setApiKey(SHIPPO_APITOKEN);

				// Optional defaults to false
				//Shippo.setDEBUG(true);
		
				// to address
				BusinessAddress toAddress=businessShippo.getToAddress();
				BusinessAddress fromAddress=businessShippo.getFromAddress();
				BusinessParcel businessParcel=businessShippo.getParcel();
		
		
				Map<String, Object> toAddressMap = new HashMap<String, Object>();
				toAddressMap.put(BusinessAddress.LABEL_NAME, toAddress.getName());
				toAddressMap.put(BusinessAddress.LABEL_COMPANY, toAddress.getCompany());
				toAddressMap.put(BusinessAddress.LABEL_STREET1, toAddress.getStreet1());
				toAddressMap.put(BusinessAddress.LABEL_CITY, toAddress.getCity());
				toAddressMap.put(BusinessAddress.LABEL_STATE, toAddress.getState());
				toAddressMap.put(BusinessAddress.LABEL_ZIP, toAddress.getZip());
				toAddressMap.put(BusinessAddress.LABEL_COUNTRY, toAddress.getCountry());
				toAddressMap.put(BusinessAddress.LABEL_PHONE, toAddress.getPhone());
				toAddressMap.put(BusinessAddress.LABEL_EMAIL, toAddress.getEmail());
		
				Map<String, Object> fromAddressMap = new HashMap<String, Object>();
				fromAddressMap.put(BusinessAddress.LABEL_NAME, fromAddress.getName());
				fromAddressMap.put(BusinessAddress.LABEL_COMPANY, fromAddress.getCompany());
				fromAddressMap.put(BusinessAddress.LABEL_STREET1, fromAddress.getStreet1());
				fromAddressMap.put(BusinessAddress.LABEL_CITY, fromAddress.getCity());
				fromAddressMap.put(BusinessAddress.LABEL_STATE, fromAddress.getState());
				fromAddressMap.put(BusinessAddress.LABEL_ZIP, fromAddress.getZip());
				fromAddressMap.put(BusinessAddress.LABEL_COUNTRY, fromAddress.getCountry());
				fromAddressMap.put(BusinessAddress.LABEL_PHONE, fromAddress.getPhone());
				fromAddressMap.put(BusinessAddress.LABEL_EMAIL, fromAddress.getEmail());
		
				// parcel
				Map<String, Object> parcelMap = new HashMap<String, Object>();
				parcelMap.put(BusinessParcel.LABEL_LENGTH, businessParcel.getLength());
				parcelMap.put(BusinessParcel.LABEL_WIDTH, businessParcel.getWidth());
				parcelMap.put(BusinessParcel.LABEL_HEIGHT, businessParcel.getHeight());
				parcelMap.put(BusinessParcel.LABEL_DISTANCEUNIT, businessParcel.getDistanceUnit());
				parcelMap.put(BusinessParcel.LABEL_WEIGHT, businessParcel.getWeight());
				parcelMap.put(BusinessParcel.LABEL_MASSUNIT, businessParcel.getMassUnit());
				List<Map<String, Object>> parcels = new ArrayList<Map<String, Object>>();
				parcels.add(parcelMap);
		
				Map<String, Object> shipmentMap = new HashMap<String, Object>();
				shipmentMap.put("address_to", toAddressMap);
				shipmentMap.put("address_from", fromAddressMap);
				shipmentMap.put("parcels", parcels);
				shipmentMap.put("async", false);
				Shipment shipment=Shipment.create(shipmentMap);
				Map<String, Object> objectMap = new HashMap<String, Object>();
		        objectMap.put("id", shipment.getObjectId());
		        objectMap.put("currency_code", "USD");
		        
		        Shipment.getShippingRates(objectMap);
	            // Allow five seconds to pass for server rates generation
	            Thread.sleep(5000);
	            
				
		return Shipment.getShippingRates(objectMap);
	}
	return null;
	
	
}

/*public Transaction getRateLable(Rate rate) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException{
	System.out.println("Getting shipping label..");
	Map<String, Object> transParams = new HashMap<String, Object>();
	transParams.put("rate", rate.getObjectId());
	transParams.put("async", false);
	Transaction transaction = Transaction.create(transParams);
	return transaction;

}*/
  
  
}
