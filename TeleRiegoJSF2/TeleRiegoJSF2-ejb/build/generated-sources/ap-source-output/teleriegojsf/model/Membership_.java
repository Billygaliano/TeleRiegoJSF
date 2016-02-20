package teleriegojsf.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import teleriegojsf.model.Land;
import teleriegojsf.model.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-20T10:23:23")
@StaticMetamodel(Membership.class)
public class Membership_ { 

    public static volatile CollectionAttribute<Membership, Transaction> transactionCollection;
    public static volatile SingularAttribute<Membership, byte[]> image;
    public static volatile CollectionAttribute<Membership, Land> landCollection;
    public static volatile SingularAttribute<Membership, String> address;
    public static volatile SingularAttribute<Membership, String> role;
    public static volatile SingularAttribute<Membership, String> salt;
    public static volatile SingularAttribute<Membership, String> userName;
    public static volatile SingularAttribute<Membership, BigInteger> iterations;
    public static volatile SingularAttribute<Membership, String> accountid;
    public static volatile SingularAttribute<Membership, String> password;
    public static volatile SingularAttribute<Membership, BigDecimal> memberNumber;
    public static volatile SingularAttribute<Membership, BigInteger> phone;
    public static volatile SingularAttribute<Membership, String> surname;
    public static volatile SingularAttribute<Membership, String> dni;
    public static volatile SingularAttribute<Membership, String> email;

}