package com.readlearncode.dukesbookshop.restserver.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by navid on 1/27/18.
 */
@XmlRootElement
public enum OrderStatus {
    ON_HOLD, CONFIRMED, PREPARING, DELIVERED, PAYED
}
