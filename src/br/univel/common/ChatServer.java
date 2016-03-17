package br.univel.common;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ChatServer extends Remote{
	
	public static final String CHAT = "SERVICO_CHAT";
	
	//registra os clientes em uma lista para o servidor saber quem esta OnLine 
	public void registrar(String nome, ChatClient client) throws RemoteException;
	
	// envia a mensagem recebida de um cliente ao seu destinatario.
	public String enviarMsgPrivada(String from, String to, String msg) throws RemoteException;
	
	//envia uma mensagem de algun cliente a todos os destinatarios da lista de clientes
	public String enviarMsgPublica(String from, String msg) throws RemoteException;
	
	// notifica os usuarios quando algun cliente desconecta do servidor
	public void logoff(String who) throws RemoteException;
	
}
