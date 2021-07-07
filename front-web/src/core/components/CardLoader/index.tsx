import ContentLoader from "react-content-loader"
import './styles.scss';

const CardLoader = () => (
  <ContentLoader 
    speed={2}
    backgroundColor="#f3f3f3"
    foregroundColor="#aaaeb1"
    className="base-card-loader"
  >
    <rect x="0" y="5" rx="0" ry="0" width="100%" height="80" />
  </ContentLoader>
)

export default CardLoader