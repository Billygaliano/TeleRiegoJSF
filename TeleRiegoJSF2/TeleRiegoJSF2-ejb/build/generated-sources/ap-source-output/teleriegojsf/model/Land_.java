package teleriegojsf.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import teleriegojsf.model.Membership;
import teleriegojsf.model.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-21T09:37:10")
@StaticMetamodel(Land.class)
public class Land_ { 

    public static volatile CollectionAttribute<Land, Transaction> transactionCollection;
    public static volatile SingularAttribute<Land, Date> lastDateIrrigation;
    public static volatile SingularAttribute<Land, BigInteger> idAdmin;
    public static volatile SingularAttribute<Land, BigInteger> squareMeters;
    public static volatile SingularAttribute<Land, BigDecimal> landId;
    public static volatile SingularAttribute<Land, Membership> memberNumber;
    public static volatile SingularAttribute<Land, String> latitude;
    public static volatile SingularAttribute<Land, String> nameland;
    public static volatile SingularAttribute<Land, BigInteger> humidity;
    public static volatile SingularAttribute<Land, BigInteger> wMAvailable;
    public static volatile SingularAttribute<Land, String> state;
    public static volatile SingularAttribute<Land, String> longitude;

}