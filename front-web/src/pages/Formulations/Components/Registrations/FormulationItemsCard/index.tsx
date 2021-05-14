import { FormulationItem } from 'core/types/Formulation'
import './styles.scss';

type Props = {
    formulationItems: FormulationItem;
}

function FormulationItemsCard({ formulationItems }: Props) {
    return (
        <div className="formulation-item-card-main">
            <div className="formulation-item-card-inf">
                <h6 className="formulation-item-card-inf-name">{formulationItems.product.name}</h6>
                <h6 className="formulation-item-card-inf-quantity">{formulationItems.quantity} {formulationItems.product.unity.id} </h6>
                <h6 className="formulation-item-card-inf-round">Arre.:{formulationItems.round ? 'Sim' : 'Não'}</h6>
                <h6 className="formulation-item-card-inf-raw-material">M.P:{formulationItems.rawMaterial  ? 'Sim' : 'Não'}</h6>
                <h6 className="formulation-item-card-inf-type">{formulationItems.type}</h6>
                <div className="formulation-item-card-actions">
                    <button
                        className="btn btn-primary btn-sm formulation-item-card-actions-btn-edit"
                    >
                        Editar
                </button>

                    <button
                        className="btn btn-danger btn-sm"
                    >
                        Excluir
                </button>
                </div>

            </div>
        </div>
    );
}
export default FormulationItemsCard;
