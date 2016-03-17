package br.univel.client;

import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.GregorianCalendar;
import java.util.Scanner;

import br.univel.common.ChatClient;
import br.univel.common.ChatServer;
import br.univel.server.AppServer;

public class AppClient implements ChatClient, Serializable{
		
	@Override
	public void receberMsgPrivada(String from, String msg) throws RemoteException {
		System.out.println( "["+from+"]" +" Diz para vocъ "+ msg);
		
	}

	
	@Override
	public void receberMsgPublica(String from, String msg) throws RemoteException {
		System.out.println( "["+from+"]"+" Diz para todos: " + msg);
		
	}

	@Override
	public void notificarEntrada(String nome) throws RemoteException {
		System.out.println("["+nome+"]" +" Entrou na Sala! \n");
		
	}

	@Override
	public void notificarSaida(String nome) throws RemoteException {
		System.out.println("["+nome+"]" + "Saiu do Chat");
		
	}
	
	public static void main(String[] args) {
		 
		Registry registry;
		ChatServer servico;
		
	
		try {
			registry = LocateRegistry.getRegistry("127.0.0.1",1818); //localiza o registro no IP e porta Ex: Mural
			servico = (ChatServer) registry.lookup(ChatServer.CHAT); // procura o nome e localiza na rede
			
			
		}catch (RemoteException e) {
			// TODO: handle exception
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menu();
		
		
	}

	private static void menu() {
		Scanner sc = new Scanner(System.in);
		int valor = 0;
		
		System.out.println("________________");
		System.out.println("|CHAT CONVERSE |");
		System.out.println("ииииииииииииииии \n");		
		System.out.println("1 - Entrar no Chat");
		System.out.println("2 - Sair");
		
		valor = sc.nextInt();
		
		while(valor != 9){
			
			switch (valor) {
			case 1:
				entrar();
				break;
			case 2:
				break;
			default:
				valor = 9;
				break;
			}
			
			
			
		}
		
	}

	public static void entrar() {
		
		Scanner sc = new Scanner(System.in);
		String usuario;
		int valor = 1;
		
		System.out.println("informe Nome usuario;");
		usuario = sc.nextLine();
				
					 
			Registry registry;
			ChatServer servico;
		try {
			registry = LocateRegistry.getRegistry("127.0.0.1",1818);
			servico = (ChatServer) registry.lookup(ChatServer.CHAT);
			
			ChatClient cliente = (ChatClient) UnicastRemoteObject.exportObject(new AppClient(), 0);
			
			servico.registrar(usuario, cliente);
			
			
				
			
		} catch (RemoteException e) {
			System.out.println("ERRO REMOTE NA AppClient");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("ERRO NotBound NA AppClient");
			e.printStackTrace();
		}
		boolean sair = true;
		while(sair){
			
			System.out.println("1 - enviar uma mensagem privada");
			System.out.println("2 - enviar uma mensagem publica");
			System.out.println("3 - sair");
			valor = sc.nextInt();
			
			switch (valor) {
			case 1:
				msgPrivada(usuario);
			case 2:
				msgPublica(usuario);
			case 3:
				
			try {
				registry = LocateRegistry.getRegistry("127.0.0.1",1818); //localiza o registro no IP e porta Ex: Mural
				servico = (ChatServer) registry.lookup(ChatServer.CHAT); // procura o nome e localiza na rede
				
				ChatClient cliente = (ChatClient) UnicastRemoteObject.exportObject(new AppClient(), 0);
				
				servico.registrar(usuario, cliente);
				
				
			}catch (RemoteException e) {
				// TODO: handle exception
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			default:
				sair = false;
				break;
			}
		}
		
		
		
	}


	private static void msgPrivada(String usuario){
		Scanner s = new Scanner(System.in);
		String de, para, msg;
		
		de = usuario;
		System.out.println("DE:" + usuario);
		System.out.println("PARA:");
		para = s.nextLine();
		System.out.println("___/\\________________________________________");
		msg = s.nextLine();
		
		Registry registry ;
		try {
			registry = LocateRegistry.getRegistry("127.0.0.1",1818);
			ChatServer servico = (ChatServer) registry.lookup(ChatServer.CHAT);
			String retorno = servico.enviarMsgPrivada(de, para, msg);
			
			System.out.println(retorno);
		
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	private static void msgPublica(String usuario) {
		Scanner s = new Scanner(System.in);
		String de, msg;
		
		de = usuario;
		System.out.println("DE:" + usuario);
		System.out.println("___/\\________________________________________");
		msg = s.nextLine();
		
		Registry registry ;
		try {
			registry = LocateRegistry.getRegistry("127.0.0.1",1818);
			ChatServer servico = (ChatServer) registry.lookup(ChatServer.CHAT);
			String retorno = servico.enviarMsgPublica(de, msg);
			
			System.out.println(retorno);
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
