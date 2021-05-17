import { FormulationItem } from 'core/types/Formulation'
import './styles.scss';

type Props = {
    formulationItems: FormulationItem;
    onDeleteItem: (formulationItem:FormulationItem) =>void;
    onEditItem: (formulationItem:FormulationItem) =>void;
}

function FormulationItemsCard({ formulationItems,onDeleteItem,onEditItem }: Props) {

    const onEdit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) =>{
        e.preventDefault();
        onEditItem(formulationItems)
    } 
    const onDelete = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) =>{
        e.preventDefault();
        onDeleteItem(formulationItems)
    } 
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
                        onClick={e=> onEdit(e)}
                    >
                        Editar
                </button>

                    <button
                        className="btn btn-danger btn-sm"
                        onClick={e=> onDelete(e)}
                    >
                        Excluir
                </button>
                </div>

            </div>
        </div>
    );
}
export default FormulationItemsCard;
