# JML verification report

## Scope and setup

JML contracts were added to the bean classes in `src/model/bean`: `AddressBean`, `AdminBean`, `CartBean`, `CustomerBean`, `InvoiceBean`, `OrdersBean`, `PaymentMethodBean` and `ProductBean`, including `OrdersBean.PurchaseInfo`. The specifications mainly describe getters, setters and selected constructors and cart operations. They describe existing behavior rather than enforcing new business rules; nullable references and floating-point `NaN` values were taken into account.

The Maven `jml` profile invokes OpenJML with `--check` during `verify`. It checks JML syntax and types, but does not prove contracts or enable runtime assertion checking. OpenJML must be installed and accessible on `PATH`, or its executable supplied through `-Dopenjml.executable=...`.

```bash
mvn -Pjml verify
```

The Docker build runs `mvn package` without this profile, so it does not run JML checks. Ordinary Java compilation ignores JML comments.

## Contract and proof work

- Most accessor contracts are private: getters specify their result and no assignments; setters specify the field they may change and its resulting value. Private contracts cannot be used by callers in other classes.
- `ProductBean.getProductId()` has a public contract; `spec_public` makes its private field visible to JML specifications without changing Java visibility. This removed the earlier visibility warnings in `CartBean`.
- `OrdersBean.products` is declared non-null for JML. Adding `assignable \nothing` to the `PurchaseInfo` constructor contract lets OpenJML retain information about existing state across construction; the reported `addProducts` null-dereference proof failure then disappeared. Its preconditions are caller obligations, not runtime input validation.
- Cart operations have cases for a null and a non-null cart. Their non-null cases are deliberately weak: they allow exceptions and unrestricted assignments, and do not specify which products or quantities change. A successful proof therefore does not establish correct cart updates.
- `CartBean.getQuantity()` promises a nonnegative result on normal completion. Because the available `Map.size()` specification did not establish that property, a runtime check was added: a negative size now causes `IllegalStateException`. A null cart can still cause `NullPointerException`. This is a Java behavior change for a nonconforming map, not merely a JML annotation.

## Verification results

The final reported run used OpenJML 21.0.29 and excluded `toString()`:

```bash
openjml --esc --progress --timeout=10 --exclude=toString --class-path 'target/classes:lib/*' --source-path src --dir src/model/bean
```

It reported **145 valid, 0 invalid, 0 timeouts, 0 errors and 4 skipped** out of 149 methods. The four skipped `toString()` methods remain unverified. Earlier, the `AddressBean` and `ProductBean` versions timed out, while the `AdminBean` and `CartBean` versions had unproved inherited `Object.toString()` obligations. A timeout is inconclusive; an unproved obligation does not necessarily mean a runtime defect.

The result proves only the obligations generated under the specifications and assumptions used by OpenJML, not complete application correctness. In particular, weak contracts can yield valid proofs without establishing the desired business behavior. No mutation testing was performed.

## Follow-up

- Define and verify stronger cart update contracts and decide the intended rules for nulls, quantities, map implementations and concurrent access.
- Review `decreaseQuantity()`: it finds an entry by product ID but removes using the supplied product, which may be a different object.
- Investigate the skipped `toString()` proofs separately and review their output for sensitive data.
- Review `OrdersBean.addProducts` specifications, map assumptions and caller obligations before interpreting its proof as a guarantee of correct map contents.