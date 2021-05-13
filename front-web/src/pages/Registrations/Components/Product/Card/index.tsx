import { Product } from 'core/types/Product';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    product:Product;
    onRemove: (productId:number) =>void;
}
const ProductCard = ({ product,onRemove }:Props) => {
    return (
        <div className="product-card">
          <div className="product-card-inf">
            <h5>{product.name}</h5>
          </div>

          <div className="product-card-action">
                <Link
                    to={`/registrations/products/${product.id}`}
                    type="button"
                    className="btn btn-outline-secondary product-card-action-btn product-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger product-card-action-btn"
                    onClick={() => onRemove(product.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default ProductCard;
