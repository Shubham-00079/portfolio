import CollectionPage from "../../components/CollectionPage";
import { catalog } from "./catalog";

export default function AdminSkills() {
  return <CollectionPage definition={catalog.skills} />;
}
