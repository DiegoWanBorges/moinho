import { ProductionOrder } from 'core/types/ProductionOrder';
import history from 'core/utils/history';
import './styles.scss';

type Props = {
    productionOrder: ProductionOrder;
    onRemove: (productionOrderId: number) => void;
}
const ProductionOrderCard = ({ productionOrder, onRemove }: Props) => {
    const onEdit = () => {
        history.push(`/productions/registrations/${productionOrder.id}`)
    }
    return (
        <div className="ProductionOrderCard-card">

            <div className="ProductionOrderCard-card-inf">
                <h5>{`O.P: ${productionOrder.id} - ${productionOrder.formulation.description}`}</h5>
                <small>{`Dt. Inicio: ${productionOrder.startDate === null ? `-` : productionOrder.startDate}`}</small>
                <small>{` Dt. Fim: ${productionOrder.endDate === null ? `-` : productionOrder.endDate}`}</small>
                <h6 >{productionOrder.status}</h6>
            </div>


            <div className="ProductionOrderCard-card-action">
                <button
                    onClick={onEdit}
                    className="btn btn-outline-secondary ProductionOrderCard-card-action-edit ">
                    {productionOrder.status === "APURACAO_FINALIZADA" ? "VISUALIZAR" : "EDITAR"}
                </button>

                <button
                    type="button"
                    className="btn btn-outline-danger ProductionOrderCard-card-action-save"
                    onClick={() => onRemove(productionOrder.id)}
                >
                    EXCLUIR
                </button>
            </div>

        </div>
    );
}
export default ProductionOrderCard;
