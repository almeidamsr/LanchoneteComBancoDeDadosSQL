//clase para criar uma tela para login do usuario.

package controle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import banco.BancoDeDados;

public class Login {

	public JLabel txtMatrizFilialLogin;
	public JTextField campoMatrizFilialLogin;
	public static String retornoMatrizFilial = null;
	public boolean validaRetornoCodLanchonete = false;
	private JFrame janela;
	private JPanel cabecalho;
	private JPanel painelDeLogin;
	private JButton botaoEntrar = new JButton("   Entrar   ");
	private JButton botaoCancelar = new JButton("Cancelar");
	
	BancoDeDados bancoDeDados = new BancoDeDados();

	// Cria a janela de login
	public void CriarJanela() {
		// Cria o cabeçalho com o titulo do programa
		cabecalho = new JPanel();
		cabecalho.setBackground(Color.LIGHT_GRAY);
		cabecalho.setBorder(new javax.swing.border.LineBorder(Color.LIGHT_GRAY, 4, true));
		cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.X_AXIS));
		cabecalho.add(Box.createHorizontalStrut(200));
		cabecalho.add(new JLabel("Lanchonete"));
		cabecalho.add(Box.createVerticalStrut(50));

		// Cria todo a interface de login onde vai o login e os butons.

		txtMatrizFilialLogin = new JLabel("Login: ", JLabel.LEFT);
		campoMatrizFilialLogin = new JTextField();

		painelDeLogin = new JPanel();
		painelDeLogin.setLayout(new GridLayout(10, 3));
		painelDeLogin.add(Box.createVerticalBox());
		painelDeLogin.add(txtMatrizFilialLogin);
		painelDeLogin.add(campoMatrizFilialLogin);
		painelDeLogin.add(Box.createVerticalBox());
		JPanel botao = new JPanel();
		botao.setLayout(new BoxLayout(botao, BoxLayout.X_AXIS));
		botao.add(Box.createHorizontalStrut(70));
		botao.add(botaoEntrar);

		//deixa o login pronto com a matricula de entrada só pra facilitar
		campoMatrizFilialLogin.setText("1234");
		
		// Executa o botao Entrar.
		botaoEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//som de clique
				playSound("CLICK10B.wav");
				
				Index index = new Index();
				Pedido pedido = new Pedido();
				
				//retornoMatrizFilial recebe o que foi digitado
				retornoMatrizFilial = campoMatrizFilialLogin.getText();
				
				//seta o valor do codigo da lanchonete na variavel retornoMatrizFilial para poder ser usado no pedido
				retornoMatrizFilial = (pedido.setMatrizFilial(campoMatrizFilialLogin.getText()));
				System.out.println("Retorno Matriz Filial : "+ retornoMatrizFilial);
				
				//metodo que seleciona a codigo da Matriz ou Filial e nome do funcionario do banco e compara com o digitado 
				validarMatrizFilialLogin();
				
				// se o que foi digitado é for invalido exibe mensagem
				if(retornoMatrizFilial.isEmpty() || retornoMatrizFilial == null || validaRetornoCodLanchonete == false){
					
					JOptionPane.showMessageDialog(null,"Matricula não cadastrada tente novamente");

					System.out.println("Não existe lanchonete cadastrada com esse numero");
					
					//campoMatrizFilialLogin.setText("  ");

				}
				else{
					//mostra a janela do index e fecha o login
					index.exibirJanelaIndex();
					janela.dispose();
				}
				

			}
		});

		botaoCancelar.setForeground(Color.red);
		botao.add(Box.createHorizontalStrut(70));
		botao.add(botaoCancelar);

		// Executa o botao cancelar.
		botaoCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				janela.dispose();
			}
		});
		painelDeLogin.add(botao);

		// Adiciona os painels no frame com um border
		janela = new JFrame("Lanchonete");
		janela.setSize(500, 600);
		janela.setLocation(450, 240);
		janela.setLayout(new BorderLayout());
		janela.add(cabecalho, BorderLayout.NORTH);
		janela.add(painelDeLogin, BorderLayout.CENTER);
		janela.add(Box.createHorizontalStrut(50), BorderLayout.EAST);
		janela.add(Box.createHorizontalStrut(50), BorderLayout.WEST);
		janela.add(new JTextField("PROG 4"), BorderLayout.SOUTH);

	}

	public void exibirJanela() {
		CriarJanela();
		janela.setLocation(450, 100);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setVisible(true);
	}

	//metodo que seleciona o codigo e o nome da lanchonete , valida se o codigo é igual ao que foi digitado, se for, validaRetornoCodLanchonete recebe true
	public void validarMatrizFilialLogin() {

		try {
			bancoDeDados.conectar();
			
			String query = "SELECT codLanchonete,nomeLanchonete FROM lanchonete_matriz_filial";
			this.bancoDeDados.resultado = this.bancoDeDados.comando.executeQuery(query);

			while (this.bancoDeDados.resultado.next()) {
				
				boolean registro = retornoMatrizFilial.equals(bancoDeDados.resultado.getString("codLanchonete"));
				
				if (registro == true && retornoMatrizFilial != null && !retornoMatrizFilial.isEmpty()) {			

					System.out.println("Lanchonete logado Codigo: " +
					this.bancoDeDados.resultado.getString("codLanchonete")+ " Nome: " +
					this.bancoDeDados.resultado.getString("nomeLanchonete"));
					
					validaRetornoCodLanchonete = true;
					}
					/*else {
							JOptionPane.showMessageDialog(null,"Matricula não cadastrada tente novamente");
							janela.dispose();
							exibirJanela();
						 }*/
			}
		} catch (Exception e) {
			System.out.println("'ValidarMatrizFilialLogin' Erro 0007: " + e.getMessage());
		}
	}
	
	//metodo pronto para chamar audio
		public void playSound(String soundName)
		 {
		   try 
		   {
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
		    Clip clip = AudioSystem.getClip( );
		    clip.open(audioInputStream);
		    clip.start( );
		   }
		   catch(Exception ex)
		   {
		     System.out.println("Error with playing sound.");
		     ex.printStackTrace( );
		   }
		 }
}