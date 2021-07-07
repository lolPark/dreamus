package com.dreamus.lolpark.purchase.constants;

public final class JpaSettingConstants {

	public static final String DIALECT_MYSQL = "org.hibernate.dialect.MySQL5Dialect";

    public static final String DIALECT_H2 = "org.hibernate.dialect.H2Dialect";

	// ======== hibernate.hbm2ddl.auto ========
	// validate
	public static final String DDL_AUTO_VALIDATE = "validate";

    // create-drop
    public static final String DDL_AUTO_CREATE_DROP = "create-drop";
    // update
    public static final String DDL_AUTO_UPDATE = "update";

	// ======== hibernate.implicit_naming_strategy ========
	public static final String DEFAULT_IMPLICIT_NAMING_STRATEGY = "org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl";

	// ======== hibernate.physical_naming_strategy ========
	public static final String DEFAULT_PHYSICAL_NAMING_STRATEGY = "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy";
}
