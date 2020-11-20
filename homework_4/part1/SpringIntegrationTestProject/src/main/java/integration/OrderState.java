package integration;

public enum OrderState {

    CANCELED,

    WAITING_FOR_PAYMENT,

    PAYMENT_COMPLETED


//    public OrderState getByName(String fromName){
//        if(fromName.contains("PAYMENT_COMPLETED")){
//            return PAYMENT_COMPLETED;
//        }else if(fromName.contains("WAITING_FOR_PAYMENT")){
//            return WAITING_FOR_PAYMENT;
//        }else{
//            return CANCELED;
//        }
//    }
}
