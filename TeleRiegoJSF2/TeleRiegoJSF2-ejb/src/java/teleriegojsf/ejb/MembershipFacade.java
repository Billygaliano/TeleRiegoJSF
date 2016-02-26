/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.ejb;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;
import teleriegojsf.model.Membership;
import teleriegojsf.model.Transaction;

/**
 *
 * @author inftel12
 */
@Stateless
public class MembershipFacade extends AbstractFacade<Membership> {
    @EJB
    private Mail mail;    
    
    private static final Random RANDOM = new SecureRandom();
    private final long MAXSIZE = 4096;
    private static final int KEY_LENGTH = 256;
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static byte[] getImage(Membership membership) {
        return membership.getImage();
        
    }
    
    @PersistenceContext(unitName = "TeleRiegoJSF2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MembershipFacade() {
        super(Membership.class);
    }
    
    public boolean changePassword(String oldPass, String newPass, BigDecimal userMemberNumber){

        Membership userDB = em.find(Membership.class,userMemberNumber);
        Boolean correctUpdate = false;
        String passwordDB = userDB.getPassword();
        String salt = userDB.getSalt();
        byte[] saltByte = hexStringToBytes(salt);
        int iterations = userDB.getIterations().intValue();
        String hashOldPassword = hashPassword(oldPass.toCharArray(),saltByte,iterations,KEY_LENGTH);
        if(hashOldPassword.equals(passwordDB)){  
            try {
                char[] password = newPass.toCharArray();
                byte[] newSalt = getNextSalt();
                String newHashPass = hashPassword(password,newSalt,iterations,KEY_LENGTH);
                
                userDB.setPassword(newHashPass);
                userDB.setSalt(bytesToHexString(newSalt));
                em.persist(userDB);
                correctUpdate = true;
            } catch (Exception ex) {
                Logger.getLogger(MembershipFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return correctUpdate;
    }
    
    public boolean testingMemberUser (BigDecimal memberNumber){
        boolean testing=false;
        Membership membership = em.find(Membership.class, memberNumber);
        if(membership!=null){
            testing=true;
        }
        return testing;
    }
    
        
    public boolean autentication(BigDecimal memberNumber,String password){
        boolean passwordAutenticated = false;
        Membership membership = em.find(Membership.class, memberNumber);
        String passwordDB = membership.getPassword();
        String salt = membership.getSalt();
        byte[] saltByte = hexStringToBytes(salt);
        int iterations = membership.getIterations().intValue();
        String hashPassword = hashPassword(password.toCharArray(),saltByte,iterations,KEY_LENGTH);
        
        if(passwordDB.equals(hashPassword)){
             passwordAutenticated = true;
        }
        return passwordAutenticated;
    }
    
    public static String hashPassword( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {
 
       try {
           SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
           PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
           SecretKey key = skf.generateSecret( spec );
           byte[] res = key.getEncoded( );
           String enc = Base64.getEncoder().encodeToString(res);
           return enc;
           
 
       } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
           throw new RuntimeException( e );
       }
   }
    
    public Membership getMembership(BigDecimal memberNumber){
        Membership membership = em.find(Membership.class, memberNumber);
        return membership;
    }
    
    public byte[] getMembershipImage(BigDecimal id) throws IOException {
        if(em.find(Membership.class, id).getImage() == null){
            
            return em.find(Membership.class, BigDecimal.ZERO).getImage();
        }else{
            return em.find(Membership.class, id).getImage();
        }
    }

    public int setMembershipImage(BigDecimal membnum, Part part) throws IOException {
        byte[] b = new byte[(int)part.getSize()];
        int i = part.getInputStream().read(b);
        if(i<0){
            return i;
        }
        Membership memberBD = em.find(Membership.class, membnum);
        memberBD.setImage(b);
        em.persist(memberBD);
       return i;
        
    }
    
        public boolean setMembershipImage(BigDecimal membnum, byte[] part) throws IOException {

        Membership memberBD = em.find(Membership.class, membnum);
        memberBD.setImage(part);
        em.persist(memberBD);
        
        return true;
    }
    
    public void sendTransactionEmail(BigDecimal idTransaction){
        Transaction transaction = em.find(Transaction.class, idTransaction); 
        String destino = transaction.getMemberNumber().getEmail();
        String asunto;
        String mensaje;
        if(transaction.getStateOrder().equals("pagado")){
            asunto ="La transacción del pedido "+transaction.getNorder()+" ha sido aceptada";
            mensaje = "Su pedido numero " + transaction.getNorder() +
                " para el terreno " + transaction.getLandId().getNameland() + " ha sido aceptado. \n"
                + " - Metros Comprados: " + transaction.getAmount() + "\n"
                + " - Precio por metros cúbicos: 0.22 € " + "\n"
                + " - Precio del pedido: " + transaction.getPrice() + " € \n\n"
                + "Gracias por realizar su pedido con Tele Riego.";
        }else{
            asunto ="La transacción del pedido "+transaction.getNorder()+" ha sido rechazada";
            mensaje = "Su pedido numero " + transaction.getNorder() +
                " ha sido rechazado. Se ha cumplido el plazo de pago de esta transacción. Debe"
                + " realizarla de nuevo si desea agua para su terreno "+transaction.getLandId().getNameland()+".";
        }
        mail = new Mail(asunto, mensaje,destino);
        mail.sendMail();

    }
        
    public void setMembershipAccountId(String accountId, BigDecimal userMemberNumber){
        Membership userDB = em.find(Membership.class,userMemberNumber);        
         
        try {  
            userDB.setAccountid(accountId);
            em.persist(userDB);
        } catch (Exception ex) {
            Logger.getLogger(MembershipFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getMembershipAccountId(BigDecimal userMemberNumber){
        Membership membership = em.find(Membership.class,userMemberNumber); 
        
        return membership.getAccountid();
    }
    
    public void deleteMembershipAccountId(BigDecimal userMemberNumber){
        Membership userDB = em.find(Membership.class,userMemberNumber);        
         
        try {  
            userDB.setAccountid(null);
            em.persist(userDB);
        } catch (Exception ex) {
            Logger.getLogger(MembershipFacade.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static byte[] getNextSalt() {
        byte[] salt = new byte[32];
        RANDOM.nextBytes(salt);
        return salt;
    }
    
    public static String bytesToHexString(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
        int v = bytes[j] & 0xFF;
        hexChars[j * 2] = hexArray[v >>> 4];
        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
    }
    
    public static byte[] hexStringToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
    
}
