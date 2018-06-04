package controle;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.JTextComponent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import banco.BancoDeDados;

public class Pedido {

	public JFrame janela;
	public JPanel cabecalho;
	public JPanel painelDoPedido;
	public JPanel rodape;
	public JComboBox comboNomeProduto; //não estou usando
	public JButton botaoOK = new JButton("               OK                 ");
	public JButton botaoConfirmarPedido = new JButton("        CONFIRMAR VENDA      ");
	public JButton botaoCancelarPedido = new JButton("         CANCELAR PEDIDO       ");
	public JButton botaoVoltar = new JButton("         VOLTAR       ");
	public JLabel txtPedido = new JLabel("Nome do Produto: ", JLabel.LEFT);
	public JTextField campoPedido = new JTextField();
	public JLabel txtQuantidade = new JLabel("Quantidade: ", JLabel.LEFT);
	public JTextField  campoQuantidade = new JTextField("1");
	public int contTipoPedido = 1;
	public ArrayList<String> arrayNomeProduto = new ArrayList<>();
	public String nomeProduto = "vazio";
	public boolean nomeValido = false;
	public int quantidadeProduto;
	public JMenuBar menuBar = new JMenuBar();
	public boolean permitirNavegar = true;
	public String codigoDoProduto;
	public boolean pedidoVendaEfetuado;
	
	BancoDeDados bancoDeDados = new BancoDeDados();
	Index index = new Index();
	Login login = new Login();

	public void CriarJanelaPedido() {
		
		cabecalho = new JPanel();
		cabecalho.setBackground(Color.orange);
		cabecalho.setBorder(new javax.swing.border.LineBorder(Color.LIGHT_GRAY, 4, true));
		cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.X_AXIS));
		cabecalho.add(Box.createHorizontalStrut(200));
		cabecalho.add(new JLabel("Lanchonete"));
		cabecalho.add(Box.createVerticalStrut(50));
		
		// guardarNomeProduto(login.retornoMatrizFilial);
		System.out.println("retorno cod pedido : "+index.retornoCodPedidoVenda);
		System.out.println("retorno cod Lanchonete : "+login.retornoMatrizFilial);

		guardarNomeProduto(login.retornoMatrizFilial);
		 
		//combo com autocomplete
		
			// the combo box (add/modify items if you like to)
		    JComboBox comboBox = new JComboBox(arrayNomeProduto.toArray());
		    // editavel
		    comboBox.setEditable(true);
		    // get the combo boxes editor component
		    JTextComponent editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
		    // change the editor's document
		    editor.setDocument(new SearchableComboBox(comboBox));
		    comboBox.setSelectedIndex(-1);		
		//--------------------------
		
//       Antiga comboBox , não estou usando		
// 		 comboNomeProduto = new JComboBox(arrayNomeProduto.toArray());
//		 comboNomeProduto.addActionListener(new ActionListener() {
//
//	            @Override
//	            public void actionPerformed(ActionEvent e) {
//	                // Do something when you select a value
//
//	            }
//	        });
		
		painelDoPedido = new JPanel();
		painelDoPedido.add(Box.createHorizontalStrut(70));
		painelDoPedido.setLayout(new GridLayout(10, 3));
        painelDoPedido.add(txtPedido);
	//	painelDoPedido.add(campoPedido);
	//	painelDoPedido.add(comboNomeProduto);
		painelDoPedido.add(comboBox);
		painelDoPedido.add(txtQuantidade);
		painelDoPedido.add(campoQuantidade);
		painelDoPedido.add(Box.createVerticalStrut(20));
		painelDoPedido.add(botaoOK);

		botaoOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				//variavel para validar se é possivel acessar o financeiro a partir da tela de pedido/venda
				permitirNavegar = false;
				
				//som de clique
				Login login = new Login();
				login.playSound("CLICK10B.wav");
				
				//pega o que foi selecionado na combo
				comboBox.getSelectedItem();
				//adiciona o que foi selecionado em uma string
				nomeProduto = comboBox.getSelectedItem()+"";
				//nomeProduto = nomeProduto.replaceAll("\t","");
				//nomeProduto = String.valueOf(nomeProduto.str());
				System.out.println(nomeProduto);
				//metodo que seleciona o nomedoProduto e valida com o que foi digitado adicionando true ou false para a variavel nomeValido
				validarNomeProduto(nomeProduto);
				
				if(nomeValido == true){
					guardarCodigoProduto(login.retornoMatrizFilial, nomeProduto);
					bancoDeDados.verificarMinimoEstoque(login.retornoMatrizFilial, codigoDoProduto);
				}
				if(bancoDeDados.produtoEstoqueVazio == false){
				//validações de campo
				//se o nome invalido e/ou vazio e/ou nulo 
				if((nomeValido == false && nomeProduto.isEmpty()) || (nomeValido == false && nomeProduto != null) || (nomeValido == false && nomeProduto == null)){
					//som de erro
					login.playSound("ComputerError.wav");
					
					JOptionPane.showMessageDialog(null, "Nome não Registrado no Sistema");
					
					//campo mostra o valor 1
					campoQuantidade.setText("1");
					quantidadeProduto = 1;
					
					//--------Por ultimo
					//zera a variavel
					nomeProduto = "vazio";
				}
				else{
					
//					//if(campoQuantidade.getText().contains("0987654321")){
					//validações de campo
					//se a quantidade do que foi digitado for diferente de 0 e não vazio
					if(campoQuantidade.getText().length() != 0 && !campoQuantidade.getText().isEmpty()){
						try {	
							//quantidadeProduto recebe o que foi digitado convertido para inteiro
							quantidadeProduto = Integer.parseInt(campoQuantidade.getText());
							if(quantidadeProduto > 0 ){
								System.out.println(quantidadeProduto);
								JOptionPane.showMessageDialog(null, "Adicionado "+nomeProduto+" "+" Quantidade: "+quantidadeProduto);
								pedidoVendaEfetuado = true;
							}else{
								campoQuantidade.setText("1");
								quantidadeProduto = 1;
								System.out.println(quantidadeProduto);
								JOptionPane.showMessageDialog(null, "Esse Campo só aceita números \nSerá creditada por padrão a quantidade 1 \n\n" +"Adicionado "+nomeProduto+" "+" Quantidade: "+quantidadeProduto,"Informação",JOptionPane.INFORMATION_MESSAGE);
							}
						}catch(NumberFormatException ex){
							campoQuantidade.setText("1");
							quantidadeProduto = 1;
							System.out.println(quantidadeProduto);
							JOptionPane.showMessageDialog(null, "Esse Campo só aceita números \nSerá creditada por padrão a quantidade 1 \n\n" +"Adicionado "+nomeProduto+" "+" Quantidade: "+quantidadeProduto,"Informação",JOptionPane.INFORMATION_MESSAGE);
						}

					}else{
						campoQuantidade.setText("1");
						quantidadeProduto = 1;
						System.out.println(quantidadeProduto);
						JOptionPane.showMessageDialog(null, "Esse Campo só aceita números \nSerá creditada por padrão a quantidade 1 \n\n" +"Adicionado "+nomeProduto+" "+" Quantidade: "+quantidadeProduto,"Informação",JOptionPane.INFORMATION_MESSAGE);
					}
										
				}//final do primeiro else do primeiro if
				
				System.out.println("nome final "+nomeProduto+" quantidade final "+quantidadeProduto);
				
				//------ Aqui vão os metodos de inserção na base de dados
					if(nomeProduto != "vazio"){
						guardarCodigoProduto(login.retornoMatrizFilial, nomeProduto);
						bancoDeDados.inserirPedidosVenda(index.retornoCodPedidoVenda, nomeProduto, login.retornoMatrizFilial, quantidadeProduto, codigoDoProduto);
						bancoDeDados.updateDiminuiEstoque(quantidadeProduto, login.retornoMatrizFilial, codigoDoProduto);
						bancoDeDados.verificarMinimoEstoque(login.retornoMatrizFilial, codigoDoProduto);
						
						if(bancoDeDados.quantidadeProdutoEstoque <= 3){
							//som de erro
							login.playSound("ComputerError.wav");
							JOptionPane.showMessageDialog(null,"O Produto "+nomeProduto+" Atingiu o minimo de estoque,\nEfetue a compra de "+bancoDeDados.ordemDeCompraEstoque+" "+nomeProduto);
						}
					}
				//-------------------------------------------------------
				}else{
					//som de erro
					login.playSound("ComputerError.wav");
					JOptionPane.showMessageDialog(null, "Produto em falta no estoque pedido de venda não registrado\nEfetue a compra de "+bancoDeDados.ordemDeCompraEstoque+" "+nomeProduto);
					
					//campo mostra o valor 1
					campoQuantidade.setText("1");
					quantidadeProduto = 1;
					
					//--------Por ultimo
					//zera a variavel
					nomeProduto = "vazio";
					bancoDeDados.produtoEstoqueVazio = false;
				}
				//--------Por ultimo
				//zera as variaveis
				nomeProduto = "vazio";
				nomeValido = false;
				campoQuantidade.setText("1");
				
			}
		});

		painelDoPedido.add(Box.createVerticalStrut(50));
		painelDoPedido.add(botaoConfirmarPedido);
		painelDoPedido.add(Box.createVerticalStrut(50));

		botaoConfirmarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//variavel para validar se é possivel acessar o financeiro a partir da tela de pedido/venda
				permitirNavegar = true;
				
				//som de clique
				Login login = new Login();
				login.playSound("CLICK10B.wav");
				
				bancoDeDados.guardaValorTotalVenda(index.retornoCodPedidoVenda);
				if(pedidoVendaEfetuado){
					//exibe a nota de venda e volta para o index
					JOptionPane.showMessageDialog(null, "Nota Fiscal \n" + " 1 \n 2 \n 3 \n 4 \n 5 \n\n Total: R$:"+bancoDeDados.somaValorVenda);
					pedidoVendaEfetuado = false;
					index.exibirJanelaIndex();
					janela.dispose();
				}else{
					//som de erro
					login.playSound("ComputerError.wav");
					JOptionPane.showMessageDialog(null,"Pedido vazio ou invalido\nVenda não pode ser efetuada");
				}
				
			}

		});
		
		
		janela = new JFrame("Pedido");
		janela.setSize(500, 600);
		janela.setLocation(450, 240);
		janela.setLayout(new BorderLayout());
		janela.add(cabecalho, BorderLayout.NORTH);
		janela.add(painelDoPedido, BorderLayout.CENTER);
		janela.add(Box.createHorizontalStrut(120), BorderLayout.EAST);
		janela.add(Box.createHorizontalStrut(120), BorderLayout.WEST);
		janela.add(new JTextField("PROG 4"), BorderLayout.SOUTH);
		janela.setBackground(Color.WHITE);
		 // Adiciona a barra de menu ao  frame
		janela.setJMenuBar(menuBar);
		

		 // Define e adiciona dois menus drop down na barra de menus
        JMenu acessoMenu = new JMenu("Acesso");
        JMenu navegarMenu = new JMenu("Navegar");
        menuBar.add(acessoMenu);
        menuBar.add(navegarMenu);
        
        // Cria e adiciona um item simples para o menu
        JMenuItem sairAction = new JMenuItem("Sair");
        JMenuItem financeiroAction = new JMenuItem("Financeiro");
        JMenuItem irParaIndexAction = new JMenuItem("Pagina Inicial");

        //menu recebe item
        acessoMenu.add(sairAction);
        navegarMenu.add(irParaIndexAction);
        navegarMenu.add(financeiroAction);
        
		 //ao clicar volta para a tela de login
		   sairAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
				       if ("Sair".equals(e.getActionCommand())) {    
							login.exibirJanela();
							janela.dispose();
					         }
				}

			});
		   
		   //ao clicar exibe financeiro
		   financeiroAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
				       if ("Financeiro".equals(e.getActionCommand())) {    
							Financeiro financeiro = new Financeiro();
							//se permitirNavegar for verdadeiro, ou seja se eu não tiver feito pedido algum
							if(permitirNavegar == true){
								financeiro.exibirJanelaFinanceiro();
								janela.dispose();
							}else{
								login.playSound("ComputerError.wav");
								JOptionPane.showMessageDialog(null,"Venda não finalizada por favor confirme a venda");
							}
						}
				}

			});
		   //pagina inicial
		   irParaIndexAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
				       if ("Pagina Inicial".equals(e.getActionCommand())) {  
				    		//se permitirNavegar for verdadeiro, ou seja se eu não tiver feito pedido algum
							if(permitirNavegar == true){
								index.exibirJanelaIndex();
								janela.dispose();
							}else{
								login.playSound("ComputerError.wav");
								JOptionPane.showMessageDialog(null,"Venda não finalizada por favor confirme a venda");
							}
					    }
				}

			});
		
	}

	public void exibirJanelaPedido() {
		CriarJanelaPedido();
		janela.setLocation(450, 100);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setVisible(true);
	}
	public void fecharJanelaPedido(){
		janela.dispose();
	}
	/*Metodo que recebe o valor de retornoMatrizFilial que foi digitado no login*/
	public String setMatrizFilial(String matrizFilial) {
		// TODO Auto-generated method stub
		this.login.retornoMatrizFilial = matrizFilial;
		return matrizFilial;
	}
	//Metodo que recebe o valor de contPedido que é gerado no botao registrar do index
	public int setCodPedido(int codPedido) {
		this.index.retornoCodPedidoVenda = codPedido;
		return codPedido;
	}
	
	//metodo que seleciona o nome do produto e guarda em uma array para ser usado na combobox
	public void guardarNomeProduto(String codLanchonete) {
		try {
			bancoDeDados.conectar();
			String query = "select nomeProduto, codProduto from lanchonete_produto where codLanchonete = '"+codLanchonete+"' order by nomeProduto;";
			this.bancoDeDados.resultado = this.bancoDeDados.comando.executeQuery(query);
			while (this.bancoDeDados.resultado.next()) {
				arrayNomeProduto.add(this.bancoDeDados.resultado.getString("nomeProduto"));
				//arrayNomeProduto.add(this.bancoDeDados.resultado.getString("codProduto")+" "+this.bancoDeDados.resultado.getString("nomeProduto"));
			}
		} catch (Exception e) {
			System.out.println("guardarNomeProduto Erro : " + e.getMessage());
		}
	}
	public void guardarCodigoProduto(String codLanchonete, String nomeDoProduto) {
		try {
			bancoDeDados.conectar();
			String query = "select codProduto from lanchonete_produto where nomeProduto = '"+nomeDoProduto+"' and codLanchonete = '"+codLanchonete+"';";
			this.bancoDeDados.resultado = this.bancoDeDados.comando.executeQuery(query);
			while (this.bancoDeDados.resultado.next()) {
				codigoDoProduto = this.bancoDeDados.resultado.getString("codProduto");
			}
		} catch (Exception e) {
			System.out.println("guardarCodigoProduto Erro : " + e.getMessage());
		}
	}
	//metodo que seleciona o nome do produto e caso o resultado da query seja nula ou nome nulo a variavel nomeValido recebe false senao true
	//para ser usado na validação dos campos
	public void validarNomeProduto(String nome) {
		try {
			bancoDeDados.conectar();
			String query = "select nomeProduto from lanchonete_produto where nomeProduto = '"+nome+"';";
			this.bancoDeDados.resultado = this.bancoDeDados.comando.executeQuery(query);
			while (this.bancoDeDados.resultado.next()) {
				ResultSet resul = bancoDeDados.resultado; 
				String getNome = this.bancoDeDados.resultado.getString("nomeProduto");
				if(resul == null || getNome == null){
					nomeValido = false;
				}else{
					nomeValido = true;
				}
			}
		} catch (Exception e) {
			System.out.println("validaNomeProduto Erro : " + e.getMessage());
		}
	}
	

	
}
