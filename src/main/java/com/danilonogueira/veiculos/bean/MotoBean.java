package com.danilonogueira.veiculos.bean;

import com.danilonogueira.veiculos.dao.MotoDAO;
import com.danilonogueira.veiculos.entity.Moto;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class MotoBean {

    private static final long serialVersionUID = 1L;

    private MotoDAO motoDAO;
    private Moto moto;
    private List<Moto> motos;

    private String estadoTela = "buscar";

    public MotoBean() {
        this.motoDAO = new MotoDAO();
        this.listar();
    }

    //CRIAR UM NOVO CARRO
    public Moto criarMoto() {
        this.moto = new Moto();
        return moto;
    }
    
    //BOTÃO NOVO
    public void novo() {
        moto = criarMoto();
        mudarInseri();
    }

    //CONFIGURAÇÃO DE MENSAGENS
    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    //VALIDA CAMPOS DE CARRO
    public boolean validaMoto(Moto moto) {
        return !(moto.getModelo().length() == 0 || moto.getFabricante().length() == 0 || moto.getCor().length() == 0 || moto.getAno() == 0);
    }

    //SALVAR UM CARRO
    public void salvar() {
        if (this.validaMoto(this.moto)) {
            if (this.motoDAO.incluir(this.moto)) {
                adicionarMensagem(moto.getFabricante()+" "+moto.getModelo()+" salvo com sucesso!", FacesMessage.SEVERITY_INFO);
                mudarBusca();
            } else {
                adicionarMensagem("Não foi possível incluir o veículo, tente novamente!", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            adicionarMensagem("Os dados estão incompletos, verifique se todas informações foram preenchidas!", FacesMessage.SEVERITY_FATAL);
        }
    }

    //DELETAR UM CARRO
    public void deletar(Moto moto) {
        if (this.motoDAO.excluir(moto)) {
            motos.remove(moto);
            adicionarMensagem(moto.getModelo()+" "+moto.getFabricante()+" excluído com sucesso!", FacesMessage.SEVERITY_INFO);
        } else {
            adicionarMensagem("Não foi possível excluir o veículo, tente novamente!", FacesMessage.SEVERITY_ERROR);
        }
    }

    //EDITAR UM CARRO
    public void editar(Moto moto) {
        this.moto = moto;
        mudarEdita();
    }

    //LISTAR OS CARROS
    public void listar() {
        if (!isBusca()) {
            mudarBusca();
            return;
        }
        try {
            motos = this.motoDAO.listar();
            if (motos == null || motos.size() < 1) {
                adicionarMensagem("Não existe nenhum dado cadastrado", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception ex) {
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    
    //METODOS PARA CONTROLE DE TELA
    public boolean isInseri() {
        return "inserir".equals(estadoTela);
    }

    public boolean isEdita() {
        return "editar".equals(estadoTela);
    }

    public boolean isBusca() {
        return "buscar".equals(estadoTela);
    }

    public void mudarInseri() {
        estadoTela = "inserir";
    }

    public void mudarEdita() {
        estadoTela = "editar";
    }

    public void mudarBusca() {
        estadoTela = "buscar";
        this.listar();
    }

    //METODOS GETTERS E SETTERS
    public List<Moto> getMotos() {
        return motos;
    }

    public void setMotos(List<Moto> motos) {
        this.motos = motos;
    }

    public String getEstadoTela() {
        return estadoTela;
    }

    public void setEstadoTela(String estadoTela) {
        this.estadoTela = estadoTela;
    }
    
    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public MotoDAO getMotoDAO() {
        return motoDAO;
    }

    public void setMotoDAO(MotoDAO motoDAO) {
        this.motoDAO = motoDAO;
    }


}
