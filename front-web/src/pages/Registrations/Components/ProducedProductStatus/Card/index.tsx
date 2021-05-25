import { ProducedProductStatus } from 'core/types/ProductionOrder';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    producedProductStatus:ProducedProductStatus;
    onRemove: (producedProductStatusId:number) =>void;
}
const ProducedproductstatusCard = ({ producedProductStatus,onRemove }:Props) => {
    return (
        <div className="producedProductStatus-card">
         
          <div className="producedProductStatus-card-inf">
            <h5>{producedProductStatus.name}</h5>
          </div>

          <div className="producedProductStatus-card-action">
                <Link
                    to={`/registrations/producedproductstatus/${producedProductStatus.id}`}
                    type="button"
                    className="btn btn-outline-secondary producedProductStatus-card-action-btn producedProductStatus-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger producedProductStatus-card-action-btn"
                    onClick={() => onRemove(producedProductStatus.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default ProducedproductstatusCard;
