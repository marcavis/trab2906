package farmacia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Medicamento {
	private int id;
	private String nome;
	private String apresentacao;
	private int quantidade;
	private int valor; 
	
	public void cadastrar() {
		String sql = "insert into medicamento (nome, apresentacao, quantidade, valor)"
				+ " values (?,?,?,?)";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getApresentacao());
			ps.setInt(3, getQuantidade());
			ps.setInt(4, getValor());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterar() {
		String sql = "update medicamento set nome=?, apresentacao=?, quantidade=?, valor=? where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getApresentacao());
			ps.setInt(3, getQuantidade());
			ps.setInt(4, getValor());
			ps.setInt(5, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir() {
		String sql = "delete from medicamento where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Medicamento> listar(String filtro) {
		ArrayList<Medicamento> lista = new ArrayList<Medicamento>();
		String sql = "select * from medicamento order by nome";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			
			//if(filtro != null) {
			//	ps.setString(1, filtro);
			//}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Medicamento m = new Medicamento();
				m.setId(rs.getInt("id"));
				m.setNome(rs.getString("nome"));
				m.setApresentacao(rs.getString("apresentacao"));
				m.setQuantidade(rs.getInt("quantidade"));
				m.setValor(rs.getInt("valor"));
				lista.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Medicamento buscaPorID(int id) {
		Medicamento m = new Medicamento();
		String sql = "select * from medicamento where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				m.setId(rs.getInt("id"));
				m.setNome(rs.getString("nome"));
				m.setApresentacao(rs.getString("apresentacao"));
				m.setQuantidade(rs.getInt("quantidade"));
				m.setValor(rs.getInt("valor"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String[] toArray() {
		return new String[]{getId() + "", getNome(), getApresentacao().substring(0, 3), getQuantidade()+"", getValor()+""};
	}

	
}
