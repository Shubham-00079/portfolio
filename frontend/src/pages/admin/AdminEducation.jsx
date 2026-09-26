import CollectionPage from "../../components/CollectionPage";
import { catalog } from "./catalog";

export default function AdminEducation() {
  return <CollectionPage definition={catalog.education} />;
}
