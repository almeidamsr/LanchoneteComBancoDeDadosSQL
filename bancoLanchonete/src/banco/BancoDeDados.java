package banco;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controle.Index;

public class BancoDeDados {

	protected Connection conect = null;
	public Statement comando = null;
	public ResultSet resultado = null;
	public String sql = null;
	public int contCodPedido;
	public String somaValorVenda;
	public int quantidadeProdutoEstoque;
	public int ordemDeCompraEstoque;
	public boolean produtoEstoqueVazio = false;

	public void conectar() {

		String servidor = "jdbc:mysql://localhost:3306/banco_6per_java";
		String usuario = "root";
		String senha = "matheus";
		String driver = "com.mysql.jdbc.Driver";

		try {
			Class.forName(driver);
			this.conect = DriverManager.getConnection(servidor, usuario, senha);
			this.comando = this.conect.createStatement();
		} catch (Exception e) {
			System.out.println("'Conectar' Erro 0001: " + e.getMessage());
		}

	}

	public boolean estaConectado() {
		if (this.conect != null) {
			return true;
		} else {
			return false;
		}
	}

	public void desconectar() {
		try {
			this.conect.close();
		} catch (Exception e) {
			System.out.println("'Desconectar' Erro 0002: " + e.getMessage());
		}
	}

	/*metodo que guarda a contagem de codPedidoVenda na variavel contCodPedidoVenda para ser usado na classe Pedido*/
	public void guardarCodPedidoVenda(int varcodPedidoVenda) {
		try {
			String query = "select max(codPedidoVenda) as codPedidoVenda from lanchonete_venda;";
			this.resultado = this.comando.executeQuery(query);
			while (this.resultado.next()) {
				contCodPedido = this.resultado.getInt("codPedidoVenda");
				//System.out.println("contCodPonto: " + this.resultado.getInt("codPedidoVenda"));
				System.out.println("contCodPonto: " + " " + varcodPedidoVenda);
			}
		} catch (Exception e) {
			System.out.println("guardarCodPedidoVenda Erro : " + e.getMessage());
		}
	}
	
	public void inserirPedidosVenda(int codPedido,String nome, String codlanchonete, int quantidade, String codigoDoProduto){
		try {
			conectar();
			
//			String query = "insert into lanchonete_venda (codPedidoVenda,nomeProduto,codProduto,quantidadeProdutoVenda,totalItensPedidoVenda,valorUnitario,valorVendaProduto,valorTotalVenda,dataVenda,horaVenda) "
//					+ "values ('"+codPedido+"','"+nome+"',(select codProduto from lanchonete_produto where nomeProduto = '"+nome+"' and codlanchonete = '"+codlanchonete+"'),'"+quantidade+"',"
//					+ "(select count(codProduto) from lanchonete_venda where codPedidoVenda = '"+codPedido+"'),(select valorUnitario from lanchonete_produto where nomeProduto = '"+nome+"' ),(select valorUnitario*quantidadeProdutoVenda from lanchonete_venda),' ',curdate(),curtime());";
			
			//,quantidadeProdutoVenda,valorUnitario,valorVendaProduto,dataVenda,horaVenda
			String query = "insert into lanchonete_venda (codPedidoVenda,nomeProduto,quantidadeProdutoVenda,valorUnitario,valorVendaProduto,dataVenda,horaVenda,codProduto) "
					+ "values('"+codPedido+"','"+nome+"','"+quantidade+"',(select distinct valorUnitario from lanchonete_produto where nomeProduto = '"+nome+"' ),"
					+ "(SELECT (select distinct valorUnitario from lanchonete_produto where nomeProduto = '"+nome+"' )*"+quantidade+"),curdate(),curtime(),'"+codigoDoProduto+"')";
			
			this.comando.executeUpdate(query);
		} catch (Exception e) {

			System.out.println(" inserirPedidosVenda Erro: " + e.getMessage());
		}
	}
	
	public void guardaValorTotalVenda(int codPedido){
		try{
			conectar();
	
			String query1 = "select sum(valorVendaProduto) as valorTotalVenda from lanchonete_venda where codPedidoVenda = '"+codPedido+"';";
	
			this.resultado = this.comando.executeQuery(query1);
	
			while (this.resultado.next()) {
					somaValorVenda = this.resultado.getString("valorTotalVenda");
					System.out.println("somaValorVenda: " + this.resultado.getString("valorTotalVenda"));		
			}
			
			} catch (Exception e) {
				System.out.println("guardaValorTotalVenda Erro: " + e.getMessage());
			}
	}
	public void updateDiminuiEstoque(int quantidadeVendida, String codigoDaLanchonete, String codigoDoProduto) {
		try {
			String query = "update lanchonete_estoque set quantidadeProduto = (quantidadeProduto)-"+quantidadeVendida+" where codLanchonete = '"+codigoDaLanchonete+"' "
					+ "and codProduto = '"+codigoDoProduto+"';";

			this.comando.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("updateDiminuiEstoque Erro : " + e.getMessage());
		}
	}
	
	public void verificarMinimoEstoque(String codigoDaLanchonete, String codigoDoProduto){
		try {
			String query = "select quantidadeProduto,ordemDeCompra from lanchonete_estoque where codLanchonete = '"+codigoDaLanchonete+"' "
					+ "and codProduto = '"+codigoDoProduto+"';";
			this.resultado = this.comando.executeQuery(query);
			while (this.resultado.next()) {
				//this.resultado.getString("quantidadeProduto");
				quantidadeProdutoEstoque = this.resultado.getInt("quantidadeProduto");
				ordemDeCompraEstoque = this.resultado.getInt("ordemDeCompra");;
				
				System.out.println("quantidadeProdutoEstoque: " + " " + quantidadeProdutoEstoque);
				System.out.println("ordemDeCompraEstoque: " + " " + ordemDeCompraEstoque);
				
				if(quantidadeProdutoEstoque < 3){
					produtoEstoqueVazio = true;
				}else{
					produtoEstoqueVazio = false;
				}
			}	
		} catch (Exception e) {
			System.out.println("verificarMinimoEstoque Erro : " + e.getMessage());
		}
	}
}
