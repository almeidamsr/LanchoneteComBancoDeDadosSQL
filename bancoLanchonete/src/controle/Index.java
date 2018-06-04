package controle;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import banco.BancoDeDados;

public class Index {

	public JFrame janela;
	public JPanel cabecalho;
	public JPanel painelDoIndex;
	public JPanel rodape;
	public JButton botaoRegistro = new JButton("                REGISTRAR PEDIDO             ");
	public JButton botaoFinanceiro = new JButton("                      FINANCEIRO                      ");
	public JMenuBar menuBar = new JMenuBar();
	public int retornoCodPedidoVenda = 1;
	
	BancoDeDados bancoDeDados = new BancoDeDados();
	Login login = new Login();
	
	public void CriarJanelaIndex() {

		Pedido pedido = new Pedido();
		
		cabecalho = new JPanel();
		cabecalho.setBackground(Color.orange);
		cabecalho.setBorder(new javax.swing.border.LineBorder(Color.LIGHT_GRAY, 4, true));
		cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.X_AXIS));
		cabecalho.add(Box.createHorizontalStrut(200));
		cabecalho.add(new JLabel("Lanchonete"));
		cabecalho.add(Box.createVerticalStrut(50));
	//	cabecalho.add(new JLabel(figura), BorderLayout.CENTER);

		painelDoIndex = new JPanel();
		painelDoIndex.setLayout(new BoxLayout(painelDoIndex, BoxLayout.Y_AXIS));
		painelDoIndex.add(Box.createVerticalStrut(70));	    
		painelDoIndex.setLayout(new GridLayout(10, 3));
		//painelDoIndex.setBackground(Color.LIGHT_GRAY);
		painelDoIndex.add(Box.createVerticalBox());		
		painelDoIndex.add(Box.createVerticalStrut(20));
		painelDoIndex.add(botaoRegistro);

		// Executa o botao registrar Index.
		botaoRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//som de clique
				Login login = new Login();
				login.playSound("CLICK10B.wav");
				
				//metodo que recebe o codigo do pedido e exibe a tela de registro
				exibirRegistrarPedidoVenda();
			}

		});

		painelDoIndex.add(Box.createVerticalStrut(50));
		painelDoIndex.add(botaoFinanceiro);
		painelDoIndex.add(Box.createVerticalStrut(50));

		botaoFinanceiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//som de clique
				Login login = new Login();
				login.playSound("CLICK10B.wav");
				
				Financeiro financeiro = new Financeiro();
				financeiro.exibirJanelaFinanceiro();
				janela.dispose();
			}

		});
		
		janela = new JFrame("Index");
		janela.setSize(500, 600);
		janela.setLocation(450, 240);
		janela.setLayout(new BorderLayout());
		janela.add(cabecalho, BorderLayout.NORTH);
		janela.add(painelDoIndex, BorderLayout.CENTER);
		janela.add(Box.createHorizontalStrut(120), BorderLayout.EAST);
		janela.add(Box.createHorizontalStrut(120), BorderLayout.WEST);
		janela.add(new JTextField("PROG 4"), BorderLayout.SOUTH);
		 // Adiciona a barra de menu ao  frame
		janela.setJMenuBar(menuBar);
		

		 // Define e adiciona dois menus drop down na barra de menus
        JMenu acessoMenu = new JMenu("Acesso");
        JMenu navegarMenu = new JMenu("Navegar");
        menuBar.add(acessoMenu);
        menuBar.add(navegarMenu);
        
        // Cria e adiciona um item simples para o menu
        JMenuItem sairAction = new JMenuItem("Sair");
        JMenuItem registrarPedidoAction = new JMenuItem("Registrar Pedido");
        JMenuItem financeiroAction = new JMenuItem("Financeiro");

        //menu recebe item
        acessoMenu.add(sairAction);
        navegarMenu.add(registrarPedidoAction);
        navegarMenu.add(financeiroAction);
        
        //metodo quando clica no menu, não esta sendo usado, somente teste
		acessoMenu.addMenuListener(  
                new MenuListener() {  
                    public void menuSelected(MenuEvent e) {  
                        System.out.println("TESTE");  
                    }  
  
                    public void menuDeselected(MenuEvent e) {} 
                    
                    public void menuCanceled(MenuEvent e) {}  
                }  
        );  
		
		 //ao clicar volta para a tela de login
		   sairAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
				       if ("Sair".equals(e.getActionCommand())) {    
							login.exibirJanela();
							janela.dispose();
					         }
				}

			});
		   //botao do menu - registrar
		   registrarPedidoAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
				       if ("Registrar Pedido".equals(e.getActionCommand())) {    
				    	   //metodo que recebe o codigo do pedido e exibe a tela de registro
				    	   exibirRegistrarPedidoVenda();
					    }
				}

			});
		   
		   //ao clicar exibe financeiro
		   financeiroAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
				       if ("Financeiro".equals(e.getActionCommand())) {    
							Financeiro financeiro = new Financeiro();
							financeiro.exibirJanelaFinanceiro();
							janela.dispose();
					         }
				}

			});
       // fileMenu.addSeparator();
        
	}

	public void exibirJanelaIndex() {
		CriarJanelaIndex();
		janela.setLocation(450, 100);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setVisible(true);
	}
	public void fecharJanelaIndex(){
		janela.dispose();
	}
	
	public void exibirRegistrarPedidoVenda(){
		bancoDeDados.conectar();	
		Pedido pedido = new Pedido();
		
		//metodo que pega o maior valor de codPedido e guarda em cont cod pedido
		bancoDeDados.guardarCodPedidoVenda(retornoCodPedidoVenda);
		
		//se contCodPedido menor que 1 contCodPedido recebe 1
		if(bancoDeDados.contCodPedido <1){
			retornoCodPedidoVenda = (pedido.setCodPedido(1));
			System.out.println("retornoCodPedidoVenda = "+retornoCodPedidoVenda);
		}
		//se contCodPedido maior ou igual a 1 contCodPedido recebe contCodPedido+1
		if(bancoDeDados.contCodPedido >= 1){
			retornoCodPedidoVenda = (pedido.setCodPedido(bancoDeDados.contCodPedido+1));
			System.out.println("retornoCodPedidoVenda = "+retornoCodPedidoVenda);
		}
		
		//exibe janela pedido que vai usar o codPedido na venda
		pedido.exibirJanelaPedido();
		janela.dispose();
	}
	
	
}
