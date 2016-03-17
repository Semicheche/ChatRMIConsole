package br.univel.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author luciano
 *
 */

public interface ChatClient extends Remote {
	
	public static final Object USUARIO = "Usuario";
	
	//serve para receber uma mensagem privada para um cliente
	public void receberMsgPrivada(String from, String msg) throws RemoteException;
	
	//serve para receber @msg de um destinatario que enviou uma mensagem a todos os clientes da lista
	public void receberMsgPublica(String from, String msg) throws RemoteException;

	//serve para notificar quando algun cliente entra na lista de cliente
	public void notificarEntrada(String nome) throws RemoteException;
	
	//serve para notificar quando algun cliente deixa a lista de cliente do servidor
	public void notificarSaida(String nome) throws RemoteException;

}
