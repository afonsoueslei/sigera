package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.controle.tela.UnidadeTela;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class UnidadeDataModel extends ListDataModel<UnidadeTela> implements SelectableDataModel<UnidadeTela> {

    public UnidadeDataModel(List<UnidadeTela> list) {
        super(list);
    }

    public UnidadeDataModel() {
    }

    @Override
    public Object getRowKey(UnidadeTela t) {
        return t.getId();
    }

    @Override
    public UnidadeTela getRowData(String numero) {
        List<UnidadeTela> unidades = (List<UnidadeTela>) getWrappedData();
        for (UnidadeTela unidade : unidades) {
            if (unidade.getId()== Integer.parseInt(numero)) {
                return unidade;
            }
        }
        return null;
    }
}
