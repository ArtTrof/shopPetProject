package com.shop.project.models;

import javax.persistence.Table;

@Table(name = "roles")
public enum Role {
    USER, ADMIN
}
