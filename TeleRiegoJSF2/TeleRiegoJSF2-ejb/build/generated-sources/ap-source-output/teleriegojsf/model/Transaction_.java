package teleriegojsf.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import teleriegojsf.model.Land;
import teleriegojsf.model.Membership;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-23T15:52:26")
@StaticMetamodel(Transaction.class)
public class Transaction_ { 

    public static volatile SingularAttribute<Transaction, String> stateOrder;
    public static volatile SingularAttribute<Transaction, Double> amount;
    public static volatile SingularAttribute<Transaction, Land> landId;
    public static volatile SingularAttribute<Transaction, Membership> memberNumber;
    public static volatile SingularAttribute<Transaction, BigDecimal> norder;
    public static volatile SingularAttribute<Transaction, Double> price;
    public static volatile SingularAttribute<Transaction, Date> dateOrder;

}