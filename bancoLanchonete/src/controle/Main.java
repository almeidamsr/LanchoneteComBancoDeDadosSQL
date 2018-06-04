package controle;

import banco.BancoDeDados;

public class Main {

	public static void main(String[] args) {
		Login login = new Login();
		login.exibirJanela();

        BancoDeDados bancoDeDados = new BancoDeDados();

		bancoDeDados.conectar();
			
			if (bancoDeDados.estaConectado()){
				System.out.println("Está conectado ao banco");
			}
			else
			{
				System.out.println("Não foi possivel conectar");
			}						
	  }
}