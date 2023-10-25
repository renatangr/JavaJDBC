package DAO;

import entity.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutoDAO { //extends GenericDAO<Produto>{
    public void inserir(Produto produto) {
        String sql = "INSERT INTO `aulapoo`.`produto` (`nome`, `quantidade`, `valorProduto`) VALUES (?,?,?)";

        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQtde());
            stmt.setDouble(3, produto.getValor());
            
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir produto: " + ex.getMessage());
        }
    }
    
    public void editar(Produto produto) {
        String sql = "UPDATE `aulapoo`.`produto` SET `nome` = ?, `quantidade` = ?, `valorProduto` = ? WHERE `produto`.`id` =?;";

        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQtde());
            stmt.setDouble(3, produto.getValor());
            stmt.setInt(4, produto.getId());
            stmt.execute();
  
        } catch (SQLException ex) {
            System.out.println("Erro ao editar produto: " + ex.getMessage());
        }
    }
    
    public boolean excluir(int codigo) {
        String sql = "DELETE FROM `aulapoo`.`produto` WHERE `produto`.`id` = ?";

        try (Connection conn = ConnectionFactory.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            return stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir produto: " + ex.getMessage());
        }
        
        return false;
    }
    
    public ArrayList<Produto> getAll() {
        String sql = "SELECT * FROM `produto`";
        ArrayList<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                int qtde = rs.getInt("quantidade");
                double valor = rs.getDouble("valorProduto");
                int id = rs.getInt("id");

                Produto prod = new Produto(id, nome, qtde, valor);
                produtos.add(prod);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar Todos produto: " + ex.getMessage());
        }

        return produtos;
    }
    
    public Produto getByID(int idParam) {
        String sql = "SELECT *  FROM `produto` WHERE `id` =?";
        Produto prod = null;

        try (Connection conn = ConnectionFactory.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, idParam);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String nome = rs.getString("nome");
                int qtde = rs.getInt("quantidade");
                double valor = rs.getDouble("valorProduto");
                int id = rs.getInt("id");

                prod = new Produto(id, nome, qtde, valor);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar Todos produto: " + ex.getMessage());
        }

        return prod;
    }
}
