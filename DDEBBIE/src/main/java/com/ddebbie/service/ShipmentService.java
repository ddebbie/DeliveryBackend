package com.ddebbie.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ddebbie.business.BusinessShippo;
import com.ddebbie.handler.ShipmentHandler;
import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.shippo.model.RateCollection;
import com.shippo.model.Shipment;

@RestController
@RequestMapping("shipment")
public class ShipmentService extends BaseService {
	@Autowired
	ShipmentHandler shipmentHandler;

	@Transactional
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public RateCollection createShipment(@RequestBody BusinessShippo businessShippo) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, InterruptedException {
		return shipmentHandler.createShipment(businessShippo);
	}

}
