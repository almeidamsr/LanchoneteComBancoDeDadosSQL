package controle;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

public class Financeiro {

	JFrame janela;
	JPanel cabecalho;
	JPanel painelDoFinanceiro;
	JPanel rodape;
	//ImageIcon figura = new ImageIcon("FinanceiroMA.png");

	JButton botaoLucro = new JButton      ("                  LUCRO DO MÊS                         ");
	JButton botaoVendasTipo = new JButton ("           VENDAS POR TIPO PRODUTO      ");
	JButton botaoFaturamento = new JButton("              FATURAMENTO POR MES          ");
	JButton botaoNotaFiscal = new JButton ("                      NOTAS FISCAIS                      ");
	JButton botaoVoltar = new JButton ("                           VOLTAR                              ");
	public JMenuBar menuBar = new JMenuBar();
	
	BancoDeDados bancoDeDados = new BancoDeDados();
	Login login = new Login();
	Pedido pedido = new Pedido();
	Index index = new Index();

	public void CriarJanelaFinanceiro() {

		cabecalho = new JPanel();
		cabecalho.setBackground(Color.orange);
		cabecalho.setBorder(new javax.swing.border.LineBorder(Color.LIGHT_GRAY, 4, true));
		cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.X_AXIS));
		cabecalho.add(Box.createHorizontalStrut(200));
		cabecalho.add(new JLabel("Lanchonete"));
		painelDoFinanceiro = new JPanel();
		painelDoFinanceiro.setLayout(new BoxLayout(painelDoFinanceiro, BoxLayout.Y_AXIS));
		painelDoFinanceiro.add(Box.createVerticalStrut(70));
		painelDoFinanceiro.add(Box.createVerticalStrut(20));
		painelDoFinanceiro.add(botaoLucro);

		// Executa o botao registrar Financeiro.
		botaoLucro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//som de clique
				Login login = new Login();
				login.playSound("CLICK10B.wav");
				
				JOptionPane.showMessageDialog(null, " |Faturamento|  |   Despesas   |  |    Lucro    |  \n"
												  + "  R$:200,00          R$:100,00         R$:100,00");

			}

		});

		painelDoFinanceiro.add(Box.createVerticalStrut(35));
		painelDoFinanceiro.add(botaoVendasTipo);
		painelDoFinanceiro.add(Box.createVerticalStrut(20));

		// Executa o botao registrar RELATORIO.
		botaoVendasTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//som de clique
				Login login = new Login();
				login.playSound("CLICK10B.wav");
				
					VendasPorTipo vendasPorTipo = new VendasPorTipo();
					vendasPorTipo.exibirJanelaVendasPorTipo();
			}

		});
		
		painelDoFinanceiro.add(Box.createVerticalStrut(20));
		painelDoFinanceiro.add(botaoFaturamento);
		painelDoFinanceiro.add(Box.createVerticalStrut(20));

		// Executa o botao registrar RELATORIO.
		botaoFaturamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//som de clique
				Login login = new Login();
				login.playSound("CLICK10B.wav");
				
				Faturamento faturamento = new Faturamento();
				faturamento.exibirJanelaFaturamento();
			}

		});
		
		painelDoFinanceiro.add(Box.createVerticalStrut(20));
		painelDoFinanceiro.add(botaoNotaFiscal);
		painelDoFinanceiro.add(Box.createVerticalStrut(20));

		botaoNotaFiscal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//som de clique
				Login login = new Login();
				login.playSound("CLICK10B.wav");
				
				JOptionPane.showMessageDialog(null, "Nota Fiscal 1 Data: 17/10/2016 \n" +" |  Produto  |   |  Valor Unitario  | \n Valor Total: R$:00,00 \n\n"+"Nota Fiscal 2 Data: 18/10/2016 \n" +" |  Produto  |   |  Valor Unitario  | \n Valor Total: R$:00,00 ");
			}

		});
		
		painelDoFinanceiro.add(Box.createVerticalStrut(20));
		painelDoFinanceiro.add(botaoVoltar);
		painelDoFinanceiro.add(Box.createVerticalStrut(20));
		botaoVoltar.setForeground(Color.red);
		botaoVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//som de clique
				Login login = new Login();
				login.playSound("CLICK10B.wav");
				
					index.exibirJanelaIndex();
					janela.dispose();
			}

		});
		
		janela = new JFrame("Financeiro");
		janela.setSize(500, 600);
		janela.setLocation(450, 240);
		janela.setLayout(new BorderLayout());
		janela.add(cabecalho, BorderLayout.NORTH);
		janela.add(painelDoFinanceiro, BorderLayout.CENTER);
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
        JMenuItem irParaIndexAction = new JMenuItem("Pagina Inicial");

        //menu recebe item
        acessoMenu.add(sairAction);
        navegarMenu.add(irParaIndexAction);
        		
		 //ao clicar volta para a tela de login
		   sairAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
				       if ("Sair".equals(e.getActionCommand())) {    
							login.exibirJanela();
							janela.dispose();
					         }
				}

			});
		   //pagina inicial
		   irParaIndexAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
				       if ("Pagina Inicial".equals(e.getActionCommand())) {    
				    	   index.exibirJanelaIndex();
				    	   janela.dispose();
					    }
				}

			});
	}

	public void exibirJanelaFinanceiro() {
		CriarJanelaFinanceiro();
		janela.setLocation(450, 100);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setVisible(true);
	}
	public void fecharJanelaFinanceiro(){
		janela.dispose();
	}

}

