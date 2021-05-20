import { ProductionOrder } from 'core/types/ProductionOrder';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    productionOrder:ProductionOrder;
    onRemove: (productionOrderId:number) =>void;
}
const ProductionOrderCard = ({ productionOrder,onRemove }:Props) => {
    return (
        <div className="group-card">
         
          <div className="group-card-inf">
            {productionOrder.id}  - 
            {productionOrder.emission} -
            {productionOrder.status} - 
            {productionOrder.formulation.description} 
          </div>
          

          <div className="group-card-action">
                <Link
                    to={`/productions/registrations/${productionOrder.id}`}
                    type="button"
                    className="btn btn-outline-secondary group-card-action-btn group-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger group-card-action-btn"
                    onClick={() => onRemove(productionOrder.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default ProductionOrderCard;
