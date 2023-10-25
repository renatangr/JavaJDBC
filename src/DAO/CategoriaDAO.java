package DAO;

import entity.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CategoriaDAO { //extends GenericDAO<Produto>{
    public void inserir(Categoria categoria) {
        String sql = "INSERT INTO `aulapoo`.`categoria` (`nome`, `descricao`) VALUES (?,?)";

        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            
            
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir categoria: " + ex.getMessage());
        }
    }
    
    public void editar(Categoria categoria) {
        String sql = "UPDATE `aulapoo`.`categoria` SET `nome` = ?, `descricao` = ? WHERE `categoria`.`id` =?;";

        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.setInt(3, categoria.getId());
            stmt.execute();
  
        } catch (SQLException ex) {
            System.out.println("Erro ao editar categoria: " + ex.getMessage());
        }
    }
    
    public boolean excluir(int codigo) {
        String sql = "DELETE FROM `aulapoo`.`categoria` WHERE `categoria`.`id` = ?";

        try (Connection conn = ConnectionFactory.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            return stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir categoria: " + ex.getMessage());
        }
        
        return false;
    }
    
    public ArrayList<Categoria> getAll() {
        String sql = "SELECT * FROM `categoria`";
        ArrayList<Categoria> categorias = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao"); 
                int id = rs.getInt("id");

                Categoria cat = new Categoria(id, nome, descricao);
                categorias.add(cat);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar todas as categorias: " + ex.getMessage());
        }

        return categorias;
    }
    
    public Categoria getByID(int idParam) {
        String sql = "SELECT *  FROM `categoria` WHERE `id` =?";
        Categoria cat = null;

        try (Connection conn = ConnectionFactory.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, idParam);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                int id = rs.getInt("id");

                cat = new Categoria(id, nome, descricao);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar a categoria: " + ex.getMessage());
        }

        return cat;
    }
}
