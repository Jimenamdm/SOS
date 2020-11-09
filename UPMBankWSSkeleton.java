/**
 * UPMBankWSSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package es.upm.fi.sos.upmbank;
    import sos.t3.a32.UPMAAClient.client.*;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.AddUser;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponseBackEnd;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordBackEnd;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordResponseE;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.ExistUser;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponseE;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.LoginBackEnd;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponse;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponseBackEnd;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserE;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserResponseE;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.UserBackEnd;
import sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.Login;

import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import es.upm.fi.sos.upmbank.xsd.AddMovementResponse;
import es.upm.fi.sos.upmbank.xsd.MovementList;
import es.upm.fi.sos.upmbank.xsd.Response;
import es.upm.fi.sos.upmbank.xsd.User;
import es.upm.fi.sos.upmbank.xsd.Username;
    /**
     *  UPMBankWSSkeleton java skeleton for the axisService
     */
    public class UPMBankWSSkeleton{


        /**
         * Auto generated method signature
         *
                                     * @param addBankAcc
             * @return addBankAccResponse
         */


        private static HashMap<String,Integer>lista = new HashMap<String,Integer>();//Sesiones activas
        private static HashMap<String,Integer>cuentas = new HashMap<String,Integer>();//Cuentas abiertas
        private static HashMap<String,ArrayList<Double>> movimientos = new HashMap<String,ArrayList<Double>>();//Movimientos del usuario
        private static HashMap<String,ArrayList<String>> cuentusr = new HashMap<String,ArrayList<String>>();//Cuentas del usuario
        private static HashMap<String,String> contraseñas = new HashMap<String,String>();
        private static HashMap<String,Double>saldos = new HashMap<String,Double>();//Saldo de cuentas
        private User miUsuario = new User();//Usuario que ha hecho login

        public UPMBankWSSkeleton(){
                User admin = new User();
                admin.setName("admin");
                admin.setPwd("admin");
        }

        public es.upm.fi.sos.upmbank.AddBankAccResponse addBankAcc
        (es.upm.fi.sos.upmbank.AddBankAcc addBankAcc){

                es.upm.fi.sos.upmbank.AddBankAccResponse respuesta = new es.upm.fi.sos.upmbank.AddBankAccResponse();
                es.upm.fi.sos.upmbank.xsd.BankAccountResponse reso = new
                es.upm.fi.sos.upmbank.xsd.BankAccountResponse();
                Random rdm = new Random();
                if(miUsuario!=null && lista.containsKey(miUsuario.getName())) {//Comprobamos si el usuario ha hecho login y si tiene una sesion activa
                reso.setIBAN("ES" + rdm.nextInt());//Generamos un IBAN aleatorio
                ArrayList<String> ceus = new ArrayList<String>();
				
                  if(!cuentas.containsKey(miUsuario.getName())){//Comprobamos si el usuario tiene ya una cuenta
                    cuentas.put(miUsuario.getName(), 1);//Si no le añadimos la primera cuenta
                    ceus.add(reso.getIBAN());
					cuentusr.put(miUsuario.getName(), ceus);
                  }else{
                     int numero = cuentas.get(miUsuario.getName());//Si sí se la añadimos a las ya existentes
                     numero += 1;
                     cuentas.put(miUsuario.getName(), numero);
                     ceus = cuentusr.get(miUsuario.getName());
					 ceus.add(reso.getIBAN());
					 cuentusr.put(miUsuario.getName(), ceus);
                   }
                  	 saldos.put(reso.getIBAN(), addBankAcc.getArgs0().getQuantity());//Actualizamos el saldo de la cuenta
                     reso.setResult(true);
                     respuesta.set_return(reso);
                     } else{
                      reso.setResult(false);
                      respuesta.set_return(reso);
                                       }
        return respuesta;
        }




        /**
         * Auto generated method signature
         *
                                     * @param closeBankAcc
             * @return closeBankAccResponse
         */

        public es.upm.fi.sos.upmbank.CloseBankAccResponse closeBankAcc
        (es.upm.fi.sos.upmbank.CloseBankAcc closeBankAcc){
               CloseBankAccResponse respuesta = new CloseBankAccResponse();
               Response res = new Response();
               String cuenta = closeBankAcc.getArgs0().getIBAN();
               ArrayList<String> ceus = new ArrayList<String>();
               if(miUsuario!=null && lista.containsKey(miUsuario.getName())){//Comprobamos si el usuario ha hecho login y tiene una sesion activa
               if(cuentusr.containsKey(miUsuario.getName())){//Comprobamos si tiene alguna cuenta abierta
               ceus = cuentusr.get(miUsuario.getName());
               if(ceus.contains(cuenta)//Comprobamos si la cuenta que intenta cerrar es la suya 
            		   && saldos.containsKey(cuenta) && saldos.get(cuenta)==0){//Comprobamos si la cuenta existe y si aún tiene saldo
                       saldos.remove(cuenta);//Eliminamos la cuenta de todos los HashMap
                       ceus.remove(cuenta);
                       cuentusr.put(miUsuario.getName(), ceus);
                       int numero = cuentas.get(miUsuario.getName());
                       numero -= 1;
                              if(numero==0){//Si era su única cuenta abierta eliminamos al usuario del HashMap
                                      cuentas.remove(miUsuario.getName());
                              }else{
                                      cuentas.put(miUsuario.getName(), numero);
                              }
                       res.setResponse(true);
                       respuesta.set_return(res);
               }else{
                       res.setResponse(false);
                       respuesta.set_return(res);
               }
               }else{
            	   res.setResponse(false);
                   respuesta.set_return(res);
               }
               }else{
            	   res.setResponse(false);
                   respuesta.set_return(res); 
               }
               
               return respuesta;
       }




        /**
         * Auto generated method signature
         *
                                     * @param logout
             * @return
         */

                 public void logout(es.upm.fi.sos.upmbank.Logout logout){

                	 int i;
                     i= lista.get(miUsuario.getName());
                           if (i > 0){
                                 i--;
                                 lista.put(miUsuario.getName(), i);//Restamos la sesión que acaba de cerrar
                                 }
                                 if(i==0){
                                     lista.remove(miUsuario.getName());//Si no hay más sesiones abiertas se elimina al usuario del HashMap de sesiones
                                     miUsuario.setName(null);
                                     miUsuario.setPwd(null);
                                 }

        }


        /**
         * Auto generated method signature
         *
                                     * @param removeUser
             * @return removeUserResponse
         */

                 public es.upm.fi.sos.upmbank.RemoveUserResponse removeUser (es.upm.fi.sos.upmbank.RemoveUser removeUser) {
                     ExistUser usuario3 = new ExistUser();
                     ExistUserResponseE resex =  new ExistUserResponseE();
                     sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.Username usre =
                             new sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.Username();
                     usre.setName(removeUser.getArgs0().getUsername());
                     usuario3.setUsername(usre);
                     es.upm.fi.sos.upmbank.RemoveUserResponse respuesta = new es.upm.fi.sos.upmbank.RemoveUserResponse();
                     UPMAuthenticationAuthorizationWSSkeletonStub stub;
                    try {
                        stub = new UPMAuthenticationAuthorizationWSSkeletonStub();
                        resex = stub.existUser(usuario3);
                        User usuar = new User();
                        usuar.setName(removeUser.getArgs0().getUsername());
                        RemoveUserE usuario = new RemoveUserE();
                        sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser usrs =
                                 new sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser();
                        usrs.setName(removeUser.getArgs0().getUsername());
                        RemoveUserResponseE aux = new RemoveUserResponseE();
                        es.upm.fi.sos.upmbank.xsd.Response reso= new es.upm.fi.sos.upmbank.xsd.Response();
                        usuario.setRemoveUser(usrs);
                        aux = stub.removeUser(usuario);
                        if(resex.get_return().getResult() && !usuar.getName().equals("admin")){//Comprobamos si existe el usuario y que no se está intentando borrar al admin
                            if (miUsuario.getName().equals("admin") && miUsuario.getPwd().equals("admin")){//Comprobamos si es el admin el que va a borrar un usuario
                                if(!cuentas.containsKey(usuar.getName())) {//Comprobamos que el usuario no tenga cuentas abiertas
                                    sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser usr =
                                        new sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser();
                    
                                    if(aux.get_return().getResult()){//Si la llamada a stub es correcta se borra al usuario
                                        if(lista.containsKey(usuar.getName()))
                                        	lista.remove(usuar.getName());
                                    	usr.setName(usuar.getName());
                                        usuario.setRemoveUser(usr);
                                        
                                    }
                                }
                            }
                        }
                        reso.setResponse(aux.get_return().getResult());
                        respuesta.set_return(reso);
                
                        } catch (AxisFault e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                

                    return respuesta;
                 }


        /**
         * Auto generated method signature
         *
                                     * @param addWithdrawal
             * @return addWithdrawalResponse
         */

                 public es.upm.fi.sos.upmbank.AddWithdrawalResponse addWithdrawal
                 (es.upm.fi.sos.upmbank.AddWithdrawal addWithdrawal){
                         es.upm.fi.sos.upmbank.AddWithdrawalResponse respuesta = new es.upm.fi.sos.upmbank.AddWithdrawalResponse();
                         AddMovementResponse res = new AddMovementResponse();
                         String cuenta = addWithdrawal.getArgs0().getIBAN();
                         double retirada = addWithdrawal.getArgs0().getQuantity();
                         ArrayList<Double> mov = new ArrayList<Double>();
                         ArrayList<String> ceus = new ArrayList<String>();
                         if(miUsuario!=null && lista.containsKey(miUsuario.getName())){//Comprobamos si el usuario ha hecho login y si tiene una sesión activa
                         if(cuentusr.containsKey(miUsuario.getName())){//Comprobamos si el usuario tiene alguna cuenta
                         ceus = cuentusr.get(miUsuario.getName());
                         if(ceus.contains(cuenta)){//Comprobamos si  la cuenta es suya
                        	 double saldo = saldos.get(cuenta);
                        	 if(saldos.containsKey(cuenta) && saldo>=retirada){//Comprobamos si la cuenta 
                        	 		saldo = saldo - retirada;//Calculamos el saldo final de la cuenta
                        	 		saldos.put(cuenta, saldo);//Actualizamos el saldo
                        	 		if(!movimientos.containsKey(miUsuario.getName())){//Si el usuario no había realizado ningún movimiento se añade el primero
                                        movimientos.put(miUsuario.getName(), mov);
                                    }
                        	 		mov = movimientos.get(miUsuario.getName());
                        	 		mov.add(0,retirada);
                        	 		movimientos.put(miUsuario.getName(), mov);//Añadimos la retirada al HashMap de movimientos
                        	 		res.setBalance(saldo);
                        	 		res.setResult(true);
                        	 		respuesta.set_return(res);
                        	 }else{
                                 res.setResult(false);
                                 respuesta.set_return(res);
                        	 }
                        	 }else{
                                 res.setResult(false);
                                 respuesta.set_return(res);
                        	 }
                         }else{
                                 res.setResult(false);
                                 respuesta.set_return(res);
                         }
                         }else{
                        	 res.setResult(false);
                             respuesta.set_return(res);
                         }
                         return respuesta;
        }



        /**
         * Auto generated method signature
         *
                                     * @param addUser
             * @return addUserResponse

         */

                 public es.upm.fi.sos.upmbank.AddUserResponse addUser(es.upm.fi.sos.upmbank.AddUser addUser){
                     es.upm.fi.sos.upmbank.AddUserResponse respuesta = new es.upm.fi.sos.upmbank.AddUserResponse();
                     UPMAuthenticationAuthorizationWSSkeletonStub stub;
                     try {
                        stub = new UPMAuthenticationAuthorizationWSSkeletonStub();
                        sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse aux =
                                 new sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse();
                        sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.AddUser aux2 =
                                 new sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.AddUser();
                        UserBackEnd usr= new UserBackEnd();
                        usr.setName(addUser.getArgs0().getUsername());
                        aux2.setUser(usr);
                        es.upm.fi.sos.upmbank.xsd.AddUserResponse reso= new es.upm.fi.sos.upmbank.xsd.AddUserResponse();
                        Username user = new Username();
                        user.setUsername(addUser.getArgs0().getUsername());
                        aux = stub.addUser(aux2);
                        if (miUsuario.getName().equals("admin") && miUsuario.getPwd().equals("admin") ){//Comprobamos si el usuario que va a añadir es el admin
                                if(aux.get_return().getResult()){//Si la llamada a stub es correcta se añade al usuario
                                    reso.setPwd(aux.get_return().getPassword());
                                    reso.setResponse(true);
                                    contraseñas.put(addUser.getArgs0().getUsername(), reso.getPwd());
                                    respuesta.set_return(reso);

                                }else{
                                    reso.setResponse(false);
                                    respuesta.set_return(reso);
                                }
                            }else{
                                reso.setResponse(false);
                                respuesta.set_return(reso);
                            }


                    } catch (AxisFault e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                return respuesta;
        }


        /**
         * Auto generated method signature
         *
                                     * @param addIncome
             * @return addIncomeResponse
         */

                 public es.upm.fi.sos.upmbank.AddIncomeResponse addIncome
                 (es.upm.fi.sos.upmbank.AddIncome addIncome){
                        es.upm.fi.sos.upmbank.AddIncomeResponse respuesta = new es.upm.fi.sos.upmbank.AddIncomeResponse();
                        AddMovementResponse res = new AddMovementResponse();
                        String cuenta = addIncome.getArgs0().getIBAN();
                        ArrayList<Double> mov = new ArrayList<Double>();
                        ArrayList<String> ceus = new ArrayList<String>();
                        if(miUsuario!=null && lista.containsKey(miUsuario.getName())){//Comprobamos si el usuario ha hecho login y si tiene una sesión activa
                        if(cuentusr.containsKey(miUsuario.getName())){//Comprobamos si el usuario tiene alguna cuenta
                        ceus = cuentusr.get(miUsuario.getName());
                        if(ceus.contains(cuenta) && saldos.containsKey(cuenta)){//Comprobamos si la cuenta existe y es suya
                                double saldo = saldos.get(cuenta);
                                double ingreso = addIncome.getArgs0().getQuantity();
                                saldo = saldo + ingreso;
                                saldos.put(cuenta,saldo);//Actualizamos el saldo
                                if(!movimientos.containsKey(miUsuario.getName())){//Si el usuario no había realizado ningún movimiento se añade el primero
                                    movimientos.put(miUsuario.getName(), mov);
                                }
                                mov = movimientos.get(miUsuario.getName());
                                mov.add(0,ingreso);
                                movimientos.put(miUsuario.getName(), mov);//Añadimos el nuevo movimiento
                                res.setBalance(saldo);
                                res.setResult(true);
                                respuesta.set_return(res);
                        }else{
                                res.setResult(false);
                                respuesta.set_return(res);
                        }
                        }else{
                        	res.setResult(false);
                            respuesta.set_return(res);
                        }
                        }else{
                        	res.setResult(false);
                            respuesta.set_return(res);
                        }

                return respuesta;
                }


        /**
         * Auto generated method signature
         *
                                     * @param login
             * @return loginResponse
         * @throws AxisFault
         */

                 public es.upm.fi.sos.upmbank.LoginResponse login(es.upm.fi.sos.upmbank.Login login) throws AxisFault{

                        UPMAuthenticationAuthorizationWSSkeletonStub cl = new UPMAuthenticationAuthorizationWSSkeletonStub();
                        cl._getServiceClient().engageModule("addressing");
                        cl._getServiceClient().getOptions().setManageSession(true);
                        Login usuario = new Login();
                        LoginBackEnd uss= new LoginBackEnd();
                        uss.setName(login.getArgs0().getName());
                        uss.setPassword(login.getArgs0().getPwd());
                        usuario.setLogin(uss);
                        es.upm.fi.sos.upmbank.LoginResponse respuesta = 
                        		new es.upm.fi.sos.upmbank.LoginResponse();
                        Response res = new Response();
                        int i=0;
                        if(miUsuario.getName()!=null){//Comprobamos que no haya ningún usuario con login
                        	if(miUsuario.getName().equals(login.getArgs0().getName())){//Comprobamos que el usuario que va a hacer login es el que se pasa por parámetro
                                         i=lista.get(usuario.getLogin().getName());
                                         i++;
                                         lista.put(usuario.getLogin().getName(), i);//Sumamos una sesión al usuario
                                         res.setResponse(true);
                                         respuesta.set_return(res);
                                 }else{
                                	 res.setResponse(false);
                                     respuesta.set_return(res);
                                 }
                         }else{
                        	 if(usuario.getLogin().getName().equals("admin")){//Comprobamos si el usuario que quiere hacer login es el admin
                        		 lista.put(usuario.getLogin().getName(),1);
                                 miUsuario.setName(login.getArgs0().getName());
                                 miUsuario.setPwd(login.getArgs0().getPwd());
                                 res.setResponse(true);
                                 respuesta.set_return(res);

                                 }else{
                                	 try {
                                		 res.setResponse(cl.login(usuario).get_return().getResult());
                                         respuesta.set_return(res);
                                         if(respuesta.get_return().getResponse()){
                                        	 lista.put(usuario.getLogin().getName(),1);
                                        	 miUsuario.setName(login.getArgs0().getName());
                                        	 miUsuario.setPwd(login.getArgs0().getPwd());
                                         }
                                         } catch
                                         (RemoteException e) {
                                        	 // TODO Auto-generated catch block
                                        	 e.printStackTrace();
                                          }
                                 }
                        	 }
                        return respuesta;

                 }


        /**
         * Auto generated method signature
         *
                                     * @param getMyMovements
             * @return getMyMovementsResponse
         */

                 public es.upm.fi.sos.upmbank.GetMyMovementsResponse getMyMovements
                  (es.upm.fi.sos.upmbank.GetMyMovements getMyMovements){
                	 es.upm.fi.sos.upmbank.GetMyMovementsResponse respuesta = new es.upm.fi.sos.upmbank.GetMyMovementsResponse();
                	 MovementList res = new MovementList();
                	 if(miUsuario!=null && lista.containsKey(miUsuario.getName())){//Comprobamos que el usuario haya hecho login y tiene una sesión activa
                		 double[]resp = null;
                		 if(movimientos.containsKey(miUsuario.getName())){//Comprobamos que el usuario tenga movimientos
                		 ArrayList<Double> dev = movimientos.get(miUsuario.getName());
                		 double longi = dev.size();
                		 if(longi>10){//Si tiene más de 10 movimientos devuelve los 10 últimos
                			  resp = new double[10];
                			 for(int i=0; i<10; i++){
                				 double aux = dev.get(i);
                				 resp[i] = aux;
                			 }
                		 }else{//Si tiene menos de 10 movimientos devuelve todos
                			 resp = new double[dev.size()];
                			 for(int i=0; i<longi; i++){
                				 double aux = dev.get(i);
                				 resp[i] = aux;
                			 }
                		 }
                		 
                		 }
                		 res.setMovementQuantities(resp);
                		 res.setResult(true);
                		 respuesta.set_return(res);
                		 	  
                	 }else{
                		 res.setResult(false);
                		 respuesta.set_return(res);
                	 }
                	 
                	 return respuesta;
        }



                 /**
                  * Auto generated method signature
                  *
                                              * @param changePassword
                      * @return changePasswordResponse
                  */
         //REVISAR COMO SABER SI EL USUARIO HA HECHO LOGIN
              public es.upm.fi.sos.upmbank.ChangePasswordResponse changePassword
                  (es.upm.fi.sos.upmbank.ChangePassword changePassword){

                  es.upm.fi.sos.upmbank.ChangePasswordResponse respuesta = new es.upm.fi.sos.upmbank.ChangePasswordResponse();
                  try {

                	  UPMAuthenticationAuthorizationWSSkeletonStub stub = new UPMAuthenticationAuthorizationWSSkeletonStub();
                	  es.upm.fi.sos.upmbank.xsd.Response reso= new es.upm.fi.sos.upmbank.xsd.Response();
                      ChangePasswordResponseE aux = new ChangePasswordResponseE();
                      sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePassword nueva2 =
                          new sos.t3.a32.UPMAAClient.client.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePassword();
                      ChangePasswordBackEnd pss = new ChangePasswordBackEnd();
                      pss.setOldpwd(contraseñas.get(miUsuario.getName()));
                      pss.setNewpwd(changePassword.getArgs0().getNewpwd());
                      pss.setName(miUsuario.getName());
                      nueva2.setChangePassword(pss);
                      aux = stub.changePassword(nueva2);
                      if(miUsuario!=null && lista.containsKey(miUsuario.getName())){//Comprobamos si el usuario ha hecho login y tiene una sesión activa
                    		  if(miUsuario.getPwd().equals(contraseñas.get(miUsuario.getName())) //Comprobamos si la contraseña se corresponde con la que pasan por parámetro
                    		  && aux.get_return().getResult()){//Comprobamos que la llamada a stub sea correcta
                    	  miUsuario.setPwd(changePassword.getArgs0().getNewpwd());//Se cambia la contraseña
                    	  contraseñas.put(miUsuario.getName(), changePassword.getArgs0().getNewpwd());//GUardamos la nueva contraseña
                          reso.setResponse(true);
                          respuesta.set_return(reso);
                      }else{
                          reso.setResponse(false);
                          respuesta.set_return(reso);
                       }
                      }else{
                    	  reso.setResponse(false);
                          respuesta.set_return(reso);
                      }
                       } catch (AxisFault e) {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       } catch (RemoteException e) {
                            // TODO Auto-generated catch block
                           e.printStackTrace();
                                                }
                 return respuesta;
                }

            }
    