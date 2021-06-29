import { Product } from 'core/types/Product';
import history from 'core/utils/history';
import './styles.scss';

type Props = {
    product: Product;
    onRemove: (productId: number) => void;
}
const ProductCard = ({ product, onRemove }: Props) => {
    const onEdit = () => {
        history.push(`/registrations/products/${product.id}`)
    }
    return (
        <div className="product-card">
            <div className="product-card-inf">
                <h5>{`${product.id} - ${product.name}`}</h5>
                <small>{`Estoque Atual: ${product.stockBalance} | Custo Médio: ${product.averageCost}`}</small>
            </div>

            <div className="product-card-action">
                <button
                    onClick={onEdit}
                    type="button"
                    className="btn btn-outline-secondary product-card-action-btn product-card-action-btn-edit">
                    EDITAR
                </button>

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
