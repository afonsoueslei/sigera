package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.controle.tela.UsuarioTelaAtribuirPerfil;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class AtribuirPerfilDataModel extends ListDataModel<UsuarioTelaAtribuirPerfil> implements SelectableDataModel<UsuarioTelaAtribuirPerfil> {

    public AtribuirPerfilDataModel() {
    }

    public AtribuirPerfilDataModel(List<UsuarioTelaAtribuirPerfil> data) {
        super(data);
    }

    @Override
    public Object getRowKey(UsuarioTelaAtribuirPerfil u) {
        return u.getId();
    }

    @Override
    public UsuarioTelaAtribuirPerfil getRowData(String id) {
        List<UsuarioTelaAtribuirPerfil> usuarios = (List<UsuarioTelaAtribuirPerfil>) getWrappedData();

        for (UsuarioTelaAtribuirPerfil usuario : usuarios) {
            if (usuario.getId() == Integer.parseInt(id)) {
                return usuario;
            }
        }

        return null;
    }
}
