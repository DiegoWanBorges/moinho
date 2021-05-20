import { ProductionOrder } from 'core/types/ProductionOrder';
import history from 'core/utils/history';
import './styles.scss';

type Props={
    productionOrder:ProductionOrder;
    onRemove: (productionOrderId:number) =>void;
}
const ProductionOrderCard = ({ productionOrder,onRemove }:Props) => {
    const onEdit =()=>{
        history.push(`/productions/registrations/${productionOrder.id}`)
    }
    return (
        <div className="group-card">
         
          <div className="group-card-inf">
            <h5>{`O.P: ${productionOrder.id} - ${productionOrder.formulation.description}`}</h5>
            <small>{
                    `Dt. Emissão: ${productionOrder.emission} / 
                     Dt. Inicio: ${productionOrder.startDate ===null ? `-` : productionOrder.startDate  } / 
                     Dt. Fim: ${productionOrder.endDate ===null ? `-` : productionOrder.endDate  }`}
            </small>
            <h6>{productionOrder.status}</h6>
          </div>
          

          <div className="group-card-action">
                <button
                    onClick={onEdit}
                    className="btn btn-outline-secondary group-card-action-edit ">
                    EDITAR
                </button>

                <button
                    type="button"
                    className="btn btn-outline-danger group-card-action-save"
                    onClick={() => onRemove(productionOrder.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default ProductionOrderCard;
