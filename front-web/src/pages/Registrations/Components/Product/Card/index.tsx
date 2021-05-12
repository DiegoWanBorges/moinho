import { Product } from 'core/types/Product';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    product:Product;
    onRemove: (productId:number) =>void;
}
const ProductCard = ({ product,onRemove }:Props) => {
    return (
        <div className="user-card">
          <div className="user-card-inf">
            <h5>{product.name}</h5>
          </div>

          <div className="user-card-action">
                <Link
                    to={`/registrations/products/${product.id}`}
                    type="button"
                    className="btn btn-outline-secondary user-card-action-btn user-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger user-card-action-btn"
                    onClick={() => onRemove(product.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default ProductCard;
