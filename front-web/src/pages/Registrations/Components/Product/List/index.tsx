import CardLoader from 'core/components/CardLoader';
import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { ProductsResponse } from 'core/types/Product';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import ProductCard from '../Card';

import './styles.scss';

function ProductList() {
    const [productsResponse, setProductsResponse] = useState<ProductsResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
    const [linesPerPage, setLinesPerPage] = useState(10);
    const [isLoading, setIsLoading] = useState(false);
    const getProducts = useCallback(() => {
        setIsLoading(true)
        const params = {
            page: activePage,
            linesPerPage: linesPerPage,
            name: name,
            orderBy: "name",
            direction: "ASC"
        }
        makePrivateRequest({ url: '/products', params })
            .then(response => setProductsResponse(response.data))
            .finally(() => {
                setIsLoading(false)
            })
    }, [activePage, name, linesPerPage])

    useEffect(() => {
        getProducts();
    }, [getProducts])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/products/new");
    }
    const onRemove = (productId: number) => {
        const confirm = window.confirm("Deseja excluir o produto selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/products/${productId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Produto excluido com sucesso!")
                    history.push('/registrations/products')
                    getProducts();
                })
                .catch(() => {
                    toast.error("Falha ao excluir produto!")
                    history.push('/registrations/products')
                })
        }
    }
    const handleChangeName = (name: string) => {
        setActivePage(0);
        setName(name);
    }
    const handleChangeLinesPerPage = (linesPerPage: number) => {
        setActivePage(0);
        setLinesPerPage(linesPerPage)
    }
    const clearFilters = () => {
        setActivePage(0);
        setName('');
        setLinesPerPage(10);
    }
    return (
        <div className="product-list">
            <div className="product-list-add-filter">
                <button
                    className="btn btn-primary btn-lg product-list-btn-add"
                    onClick={handCreate}
                >
                    ADCIONAR
                </button>
                <Filter
                    name={name}
                    handleChangeName={handleChangeName}
                    linesPerPage={linesPerPage}
                    handleChangeLinesPerPage={handleChangeLinesPerPage}
                    clearFilters={clearFilters}
                    placeholder="Digite o nome do produto"
                />
            </div>
            <div className="admin-list-container">
                {isLoading ? <CardLoader /> : (
                    productsResponse?.content.map(product => (
                        <ProductCard
                            product={product} key={product.id}
                            onRemove={onRemove}
                        />
                    )))}

                {productsResponse &&
                    <Pagination
                        totalPages={productsResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default ProductList;
