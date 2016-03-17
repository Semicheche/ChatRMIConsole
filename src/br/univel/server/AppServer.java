package br.univel.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import br.univel.client.AppClient;
import br.univel.common.ChatClient;
import br.univel.common.ChatServer;


import java.util.Map.Entry;

public class AppServer implements ChatServer {
/**
 * @author luciano
 * 
 * essa aplicação inicia primeiro e espera os clientes se conectarem
 * 
 * 
 */
	
	//hashMap de clientes
	private Map<String, ChatClient> mapClientes = new HashMap<>();
	
	ChatClient client;
	
	@Override
	public void registrar(String nome, ChatClient client) throws RemoteException {
		this.client = client;
		//verifica os clientes e envia os 
		if(mapClientes.containsKey(nome))
				throw new RemoteException("Esse nome já Esta sendo usado.");
			
		mapClientes.put(nome, client);
		System.out.println("On line: " + nome );
		
	}

	
	
	@Override
	public String enviarMsgPrivada(String from, String to, String msg) throws RemoteException {
		
		System.out.println("Enviando Mensagem...\n");
		//veifica se o cliente de destino se encontra na lista do servidor se sim ele encaminha a mensagen
		mapClientes.get(to).receberMsgPrivada(from, msg);
		
		return "Mensagem Enviada com Sucesso";
		
	}

	
	@Override
	public String enviarMsgPublica(String from, String msg) throws RemoteException {

		//envia a mensagem a todos os clientes das lista menos a si propio
		
		for (Entry<String, ChatClient> e : mapClientes.entrySet()){
			
			if(e.getKey().equals(from)){
				continue;
			} else {
				e.getValue().receberMsgPublica(from, msg);
			}
		}
		return "Mensagem Enviada com Sucesso";
		
	}

	
	
	@Override
	public void logoff(String who) throws RemoteException {
		mapClientes.remove(who);
		
	}
	//metodo main inicia o Servidor
	public static void main(String[] args) {
		System.out.println("Iniciando o AppServer...");

		AppServer s = new AppServer();///cria um objeto
		ChatServer servico;
		
		try {
			servico = (ChatServer) UnicastRemoteObject.exportObject(s, 0);// e o exporta o objeto remoto na vaivel registry
			Registry registry = LocateRegistry.createRegistry(1818); // registra o objeto remoto e diponibiliza como em um mural
			registry.rebind(ChatServer.CHAT, servico);// e coloca o nome com um nome para o cliente localizado
		
			
		} catch (RemoteException e) {
			System.out.println("Error AppServer!!!");
			e.printStackTrace();
		}
	}

}
